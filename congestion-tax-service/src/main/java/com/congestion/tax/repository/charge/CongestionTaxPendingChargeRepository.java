package com.congestion.tax.repository.charge;

import com.congestion.tax.entity.charge.CongestionTaxPendingChargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongestionTaxPendingChargeRepository extends JpaRepository<CongestionTaxPendingChargeEntity, Long> {
}
