package com.congestion.tax.model;

import java.time.ZoneId;

public record City(Long id, String name, ZoneId timezone) {
}
