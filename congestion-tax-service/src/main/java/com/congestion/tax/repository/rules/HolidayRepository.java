package com.congestion.tax.repository.rules;

import com.congestion.tax.entity.rules.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
}
