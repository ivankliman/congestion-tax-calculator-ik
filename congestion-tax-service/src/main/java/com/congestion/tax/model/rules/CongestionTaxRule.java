package com.congestion.tax.model.rules;

import com.congestion.tax.model.City;
import com.congestion.tax.model.vehicle.VehicleType;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

public record CongestionTaxRule(Long id, City city, List<DayOfWeek> exemptDaysInAWeek, List<Month> exemptMonths,
                                Boolean chargingOnHolidays, Integer daysNotChargingBeforeHoliday,
                                Integer daysNotChargingAfterHoliday, List<VehicleType> exemptVehicleTypes) {
}
