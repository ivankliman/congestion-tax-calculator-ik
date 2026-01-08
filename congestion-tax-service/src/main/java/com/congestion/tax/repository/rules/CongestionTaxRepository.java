package com.congestion.tax.repository.rules;

import com.congestion.tax.entity.rules.CongestionTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface CongestionTaxRepository extends JpaRepository<CongestionTaxEntity, Long> {
    @Query("""
    SELECT c
    FROM CongestionTaxEntity c
    WHERE c.city.id = :cityId
      AND :time BETWEEN c.startTime AND c.endTime
""")
    Optional<CongestionTaxEntity> findByCityIdAndBetweenStartTimeAndEndTime(
            @Param("cityId") Long cityId,
            @Param("time") LocalTime time
    );}
