package com.congestion.tax.model.vehicle;

import java.time.LocalDateTime;

public record VehiclePassRequest(String licensePlate, Long cityId, LocalDateTime vehiclePassedAt) {
}
