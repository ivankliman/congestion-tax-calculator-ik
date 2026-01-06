package com.congestion.tax.model.rules;

import com.congestion.tax.model.City;

import java.time.LocalTime;

public record CongestionTax(Long id, LocalTime startTime, LocalTime endTime, Double taxAmount, City city) {
}
