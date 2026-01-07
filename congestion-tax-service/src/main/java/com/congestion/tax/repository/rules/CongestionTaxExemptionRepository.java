package com.congestion.tax.repository.rules;

import com.congestion.tax.entity.rules.CongestionTaxExemptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongestionTaxExemptionRepository extends JpaRepository<CongestionTaxExemptionEntity, Long> {
}
