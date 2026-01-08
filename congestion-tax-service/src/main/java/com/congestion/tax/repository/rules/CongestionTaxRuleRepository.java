package com.congestion.tax.repository.rules;

import com.congestion.tax.entity.rules.CongestionTaxRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CongestionTaxRuleRepository extends JpaRepository<CongestionTaxRuleEntity, Long> {
    Optional<CongestionTaxRuleEntity> findByCityId(Long cityId);
}
