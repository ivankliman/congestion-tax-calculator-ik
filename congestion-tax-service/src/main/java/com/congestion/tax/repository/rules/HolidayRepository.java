package com.congestion.tax.repository.rules;

import com.congestion.tax.entity.rules.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
    List<HolidayEntity> findByHolidayDateBetween(LocalDate startDate, LocalDate endDate);
}
