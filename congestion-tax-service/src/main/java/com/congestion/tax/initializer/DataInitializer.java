package com.congestion.tax.initializer;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.rules.CongestionTaxEntity;
import com.congestion.tax.entity.rules.CongestionTaxRuleEntity;
import com.congestion.tax.entity.rules.HolidayEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import com.congestion.tax.model.vehicle.VehicleType;
import com.congestion.tax.repository.CityRepository;
import com.congestion.tax.repository.rules.CongestionTaxRepository;
import com.congestion.tax.repository.rules.CongestionTaxRuleRepository;
import com.congestion.tax.repository.rules.HolidayRepository;
import com.congestion.tax.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final CityRepository cityRepository;
    private final CongestionTaxRepository congestionTaxRepository;
    private final CongestionTaxRuleRepository congestionTaxRuleRepository;
    private final HolidayRepository holidayRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (cityRepository.count() == 0 &&
                congestionTaxRepository.count() == 0 &&
                congestionTaxRuleRepository.count() == 0 &&
                holidayRepository.count() == 0 &&
                vehicleRepository.count() == 0) {
            CityEntity city = CityEntity.builder()
                    .name("Gothenburg")
                    .timezone(ZoneId.of("Europe/Stockholm"))
                    .build();

            cityRepository.save(city);

            congestionTaxRepository.saveAll(createCongestionTaxEntities(city));

            CongestionTaxRuleEntity congestionTaxRuleEntity = CongestionTaxRuleEntity.builder()
                    .city(city)
                    .exemptDaysInAWeek(List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                    .exemptMonths(List.of(Month.JULY))
                    .chargingOnHolidays(false)
                    .daysNotChargingBeforeHoliday(1)
                    .daysNotChargingAfterHoliday(0)
                    .maxChargePerDay(60.0)
                    .exemptVehicleTypes(List.of(VehicleType.EMERGENCY, VehicleType.BUS, VehicleType.DIPLOMAT, VehicleType.MOTORCYCLE,
                            VehicleType.MILITARY, VehicleType.FOREIGN))
                    .build();

            congestionTaxRuleRepository.save(congestionTaxRuleEntity);

            holidayRepository.saveAll(createHolidaysFor2013());

            vehicleRepository.saveAll(createVehicleEntities());
        }
    }

    private List<CongestionTaxEntity> createCongestionTaxEntities(CityEntity city) {
        return List.of(
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(6, 0, 0))
                        .endTime(LocalTime.of(6, 29, 59))
                        .taxAmount(8.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(6, 30, 0))
                        .endTime(LocalTime.of(6, 59, 59))
                        .taxAmount(13.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(7, 0, 0))
                        .endTime(LocalTime.of(7, 59, 59))
                        .taxAmount(18.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(8, 0, 0))
                        .endTime(LocalTime.of(8, 29, 59))
                        .taxAmount(13.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(8, 30, 0))
                        .endTime(LocalTime.of(14, 59, 59))
                        .taxAmount(8.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(15, 0, 0))
                        .endTime(LocalTime.of(15, 29, 59))
                        .taxAmount(13.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(15, 30, 0))
                        .endTime(LocalTime.of(16, 59, 59))
                        .taxAmount(18.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(17, 0, 0))
                        .endTime(LocalTime.of(17, 59, 59))
                        .taxAmount(13.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(18, 0, 0))
                        .endTime(LocalTime.of(18, 29, 59))
                        .taxAmount(8.0)
                        .city(city)
                        .build(),
                CongestionTaxEntity.builder()
                        .startTime(LocalTime.of(18, 30, 0))
                        .endTime(LocalTime.of(5, 59, 59))
                        .taxAmount(0.0)
                        .city(city)
                        .build()
        );
    }

    private List<HolidayEntity> createHolidaysFor2013() {
        return List.of(
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 1, 1))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 1, 6))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 3, 29))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 3, 31))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 4, 1))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 5, 1))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 5, 9))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 5, 19))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 6, 6))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 6, 22))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 11, 2))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 12, 25))
                        .build(),
                HolidayEntity.builder()
                        .holidayDate(LocalDate.of(2013, 12, 26))
                        .build()
        );
    }

    private List<VehicleEntity> createVehicleEntities() {
        return List.of(
                VehicleEntity.builder()
                        .licensePlate("TEST-EMERGENCY")
                        .vehicleType(VehicleType.EMERGENCY)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-BUS")
                        .vehicleType(VehicleType.BUS)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-DIPLOMAT")
                        .vehicleType(VehicleType.DIPLOMAT)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-MOTORCYCLE")
                        .vehicleType(VehicleType.MOTORCYCLE)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-MILITARY")
                        .vehicleType(VehicleType.MILITARY)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-FOREIGN")
                        .vehicleType(VehicleType.FOREIGN)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-REGULAR-1")
                        .vehicleType(VehicleType.REGULAR)
                        .build(),
                VehicleEntity.builder()
                        .licensePlate("TEST-REGULAR-2")
                        .vehicleType(VehicleType.REGULAR)
                        .build()
        );
    }
}
