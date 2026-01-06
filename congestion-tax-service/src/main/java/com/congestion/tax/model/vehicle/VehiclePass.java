package com.congestion.tax.model.vehicle;

import com.congestion.tax.model.City;

import java.time.LocalDateTime;

public record VehiclePass(Long id, Vehicle vehicle, City city, LocalDateTime vehiclePassedAt) {
}
