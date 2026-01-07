package com.congestion.tax.repository.rules;

import com.congestion.tax.entity.rules.CongestionTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongestionTaxRepository extends JpaRepository<CongestionTaxEntity, Long> {
}
