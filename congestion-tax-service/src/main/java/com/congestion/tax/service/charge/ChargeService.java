package com.congestion.tax.service.charge;

import com.congestion.tax.entity.charge.CongestionTaxPendingChargeEntity;
import com.congestion.tax.entity.charge.VehicleDailyChargeEntity;
import com.congestion.tax.entity.rules.CongestionTaxRuleEntity;
import com.congestion.tax.entity.rules.HolidayEntity;
import com.congestion.tax.mapper.CityMapper;
import com.congestion.tax.mapper.charge.CongestionTaxPendingChargeRepositoryMapper;
import com.congestion.tax.mapper.vehicle.VehicleMapper;
import com.congestion.tax.model.vehicle.VehiclePass;
import com.congestion.tax.repository.charge.CongestionTaxPendingChargeRepository;
import com.congestion.tax.repository.charge.VehicleDailyChargeRepository;
import com.congestion.tax.repository.rules.CongestionTaxRepository;
import com.congestion.tax.repository.rules.CongestionTaxRuleRepository;
import com.congestion.tax.repository.rules.HolidayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChargeService {
    private final CongestionTaxPendingChargeRepository congestionTaxPendingChargeRepository;
    private final CongestionTaxPendingChargeRepositoryMapper congestionTaxPendingChargeRepositoryMapper;
    private final CongestionTaxRuleRepository congestionTaxRuleRepository;
    private final CongestionTaxRepository congestionTaxRepository;
    private final HolidayRepository holidayRepository;
    private final VehicleDailyChargeRepository vehicleDailyChargeRepository;
    private final CityMapper cityMapper;
    private final VehicleMapper vehicleMapper;

    public Boolean isEligibleForCharge(VehiclePass vehiclePass) {
        final var chargeTime = vehiclePass.vehiclePassedAt();

        // find rule exemptions for the city
        final var cityRuleExemptionsOptional = congestionTaxRuleRepository.findByCityId(vehiclePass.city().id());
        if (cityRuleExemptionsOptional.isEmpty()) {
            //we can assume if there are no exemptions, charging is for everyone
            return true;
        }
        final var holidays = holidayRepository.findByHolidayDateBetween(LocalDate.of(chargeTime.getYear(), 1, 1),
                LocalDate.of(chargeTime.getYear(), 12, 31));

        return checkAllRules(vehiclePass, cityRuleExemptionsOptional.get(), holidays);
    }

    public void addChargeToPendingCharges(VehiclePass vehiclePass) {
        final var chargeTime = vehiclePass.vehiclePassedAt().atZone(vehiclePass.city().timezone()).toInstant();
        final var congestionTaxEntityOptional =
                congestionTaxRepository
                        .findByCityIdAndBetweenStartTimeAndEndTime(vehiclePass.city().id(), vehiclePass.vehiclePassedAt().toLocalTime());
        if (congestionTaxEntityOptional.isEmpty() || congestionTaxEntityOptional.get().getTaxAmount().equals(0.0)) {
            log.info("No charge for vehicle with id: {} at city with id: {} at; {}",
                    vehiclePass.vehicle().id(), vehiclePass.city().id(), vehiclePass.vehiclePassedAt());
            return;
        }

        final var chargeTimeMinusOneHour = chargeTime.minus(1, ChronoUnit.HOURS);
        final var chargeRecordWithinTheSameHourOptional = congestionTaxPendingChargeRepository.findByFirstPassBetween(chargeTimeMinusOneHour, chargeTime);
        //if there is no record, or the record date is transferred to another day, create a new record
        if (chargeRecordWithinTheSameHourOptional.isEmpty() || isVehiclePassInAnotherDay(chargeRecordWithinTheSameHourOptional.get(), vehiclePass)) {
            log.info("Pending charge object not found, saving a new one");
            congestionTaxPendingChargeRepository.save(
                    congestionTaxPendingChargeRepositoryMapper.toEntity(
                            cityMapper.toEntity(vehiclePass.city()),
                            vehicleMapper.toEntity(vehiclePass.vehicle()),
                            chargeTime,
                            chargeTime,
                            congestionTaxEntityOptional.get().getTaxAmount())
            );
        }
        else {
            log.info("Pending charge object found, checking last pass and updating amount to charge");
            final var chargeRecordWithinTheSameHour = chargeRecordWithinTheSameHourOptional.get();
            chargeRecordWithinTheSameHour.setLastPass(chargeTime);
            chargeRecordWithinTheSameHour.setPrice(Math.max(chargeRecordWithinTheSameHour.getPrice(), congestionTaxEntityOptional.get().getTaxAmount()));
            congestionTaxPendingChargeRepository.save(chargeRecordWithinTheSameHour);
        }

    }

    @Transactional
    @Scheduled(cron = "${charging.cron}")
    public void handlePendingCharges() {
        //checking if the start pass happened more than one hour ago to avoid updates to that record. This is because of the single charge rule
        final var hourAndAHalfAgo = Instant.now().minus(90, ChronoUnit.MINUTES);
        final var chargesToHandle = congestionTaxPendingChargeRepository.findByFirstPassLessThanEqual(hourAndAHalfAgo);
        final var dailyMaxedRecords = new ArrayList<Long>();
        final var chargeRecordsToRemove = new ArrayList<Long>();

        for (var chargeRecord : chargesToHandle) {
            LocalDate localDateOfPendingCharge = chargeRecord.getFirstPass().atZone(chargeRecord.getCity().getTimezone()).toLocalDate();

            final var dailyChargeUntilNowOptional = vehicleDailyChargeRepository
                    .findByCityAndVehicleAndChargeDate(chargeRecord.getCity(), chargeRecord.getVehicle(),  localDateOfPendingCharge);

            //we assume that a single hour charge is less than max daily charge
            if(dailyChargeUntilNowOptional.isEmpty()) {
                final var vehicleDailyChargeEntity = VehicleDailyChargeEntity.builder()
                        .vehicle(chargeRecord.getVehicle())
                        .city(chargeRecord.getCity())
                        .chargeDate(localDateOfPendingCharge)
                        .dailyChargeAmount(chargeRecord.getPrice())
                        .build();

                vehicleDailyChargeRepository.saveAndFlush(vehicleDailyChargeEntity);
                //TODO: charge the record
                log.info("Vehicle with license plate: {} has been charged the congestion tax for date: {} in the city: {} with amount of: {}",
                        chargeRecord.getVehicle().getLicensePlate(),
                        localDateOfPendingCharge,
                        chargeRecord.getCity().getName(),
                        chargeRecord.getPrice());
                chargeRecordsToRemove.add(chargeRecord.getId());
            }
            else {
                final var congestionTaxRule = congestionTaxRuleRepository.findByCityId(chargeRecord.getCity().getId());
                final var dailyChargeUntilNow = dailyChargeUntilNowOptional.get();

                if (dailyMaxedRecords.contains(dailyChargeUntilNow.getId())) {
                    log.info("Skipping charge record with id: {} because it reached daily max charges",  chargeRecord.getId());
                    chargeRecordsToRemove.add(chargeRecord.getId());
                    continue;
                }

                if (congestionTaxRule.isPresent() && congestionTaxRule.get().getMaxChargePerDay() > 0.0) {
                    final var maxChargePerDay = congestionTaxRule.get().getMaxChargePerDay();
                    final var realChargePrice = dailyChargeUntilNow.getDailyChargeAmount() + chargeRecord.getPrice() > maxChargePerDay
                            ? maxChargePerDay - dailyChargeUntilNow.getDailyChargeAmount()
                            : chargeRecord.getPrice();

                    dailyChargeUntilNow.setDailyChargeAmount(
                            Math.min(dailyChargeUntilNow.getDailyChargeAmount() + chargeRecord.getPrice(),
                                    maxChargePerDay));

                    vehicleDailyChargeRepository.saveAndFlush(dailyChargeUntilNow);
                    //TODO: charge the record
                    log.info("Vehicle with license plate: {} has been charged the congestion tax for date: {} in the city: {} with amount of: {}",
                            chargeRecord.getVehicle().getLicensePlate(),
                            localDateOfPendingCharge,
                            chargeRecord.getCity().getName(),
                            realChargePrice);
                    chargeRecordsToRemove.add(chargeRecord.getId());
                    if(dailyChargeUntilNow.getDailyChargeAmount().equals(maxChargePerDay)) {
                        dailyMaxedRecords.add(dailyChargeUntilNow.getId());
                    }
                }
            }

        }

        congestionTaxPendingChargeRepository.deleteAllByIdInBatch(chargeRecordsToRemove);
    }

    private boolean isVehiclePassInAnotherDay(CongestionTaxPendingChargeEntity chargeRecordWithinTheSameHour, VehiclePass vehiclePass) {
        return chargeRecordWithinTheSameHour.getFirstPass().atZone(vehiclePass.city().timezone()).getDayOfMonth()
                != vehiclePass.vehiclePassedAt().getDayOfMonth();
    }

    //potential for defining predicates for it
    private Boolean checkAllRules(VehiclePass vehiclePass, CongestionTaxRuleEntity ruleEntity, List<HolidayEntity> holidays) {
        if (ruleEntity.getExemptDaysInAWeek().contains(vehiclePass.vehiclePassedAt().getDayOfWeek())) {
            log.info("Vehicle with id: {} is exempt because of the week day rule", vehiclePass.vehicle().id());
            return false;
        }
        if (ruleEntity.getExemptMonths().contains(vehiclePass.vehiclePassedAt().getMonth())) {
            log.info("Vehicle with id: {} is exempt because of the month rule", vehiclePass.vehicle().id());
            return false;
        }

        final var vehiclePassedAtDate = vehiclePass.vehiclePassedAt().toLocalDate();
        if(!ruleEntity.getChargingOnHolidays()) {
            if(holidays.stream()
                    .anyMatch(he-> vehiclePassedAtDate.equals(he.getHolidayDate()))) {
                log.info("Vehicle with id: {} is exempt because of the holiday rule", vehiclePass.vehicle().id());

                return false;
            }
        }
        if(ruleEntity.getDaysNotChargingBeforeHoliday() > 0) {
            if(holidays.stream()
                    .anyMatch(he-> vehiclePassedAtDate.isBefore(he.getHolidayDate())
                            && vehiclePassedAtDate.isAfter(he.getHolidayDate().minusDays(ruleEntity.getDaysNotChargingBeforeHoliday())))) {
                log.info("Vehicle with id: {} is exempt because of the days before the holiday rule", vehiclePass.vehicle().id());

                return false;
            }
        }
        if(ruleEntity.getDaysNotChargingAfterHoliday() > 0) {
            if(holidays.stream()
                    .anyMatch(he-> vehiclePassedAtDate.isAfter(he.getHolidayDate())
                            && vehiclePassedAtDate.isBefore(he.getHolidayDate().plusDays(ruleEntity.getDaysNotChargingAfterHoliday())))) {
                log.info("Vehicle with id: {} is exempt because of the days after the holiday rule", vehiclePass.vehicle().id());

                return false;
            }
        }
        if (ruleEntity.getExemptVehicleTypes().contains(vehiclePass.vehicle().vehicleType())) {
            log.info("Vehicle with id: {} is exempt because of the vehicle type rule", vehiclePass.vehicle().id());

            return false;
        }

        return true;
    }
}
