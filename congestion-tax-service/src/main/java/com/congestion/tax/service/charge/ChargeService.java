package com.congestion.tax.service.charge;

import com.congestion.tax.entity.rules.CongestionTaxRuleEntity;
import com.congestion.tax.entity.rules.HolidayEntity;
import com.congestion.tax.mapper.CityMapper;
import com.congestion.tax.mapper.charge.CongestionTaxPendingChargeRepositoryMapper;
import com.congestion.tax.mapper.vehicle.VehicleMapper;
import com.congestion.tax.model.vehicle.VehiclePass;
import com.congestion.tax.repository.charge.CongestionTaxPendingChargeRepository;
import com.congestion.tax.repository.rules.CongestionTaxRepository;
import com.congestion.tax.repository.rules.CongestionTaxRuleRepository;
import com.congestion.tax.repository.rules.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        if (chargeRecordWithinTheSameHourOptional.isEmpty()) {
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

    //potential of defining predicates
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
