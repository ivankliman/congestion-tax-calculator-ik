package com.congestion.tax.repository.charge;

import com.congestion.tax.entity.charge.CongestionTaxPendingChargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CongestionTaxPendingChargeRepository extends JpaRepository<CongestionTaxPendingChargeEntity, Long> {
    Optional<CongestionTaxPendingChargeEntity> findByFirstPassBetween(Instant start, Instant end);

    List<CongestionTaxPendingChargeEntity> findByFirstPassLessThanEqual(Instant time);
}
