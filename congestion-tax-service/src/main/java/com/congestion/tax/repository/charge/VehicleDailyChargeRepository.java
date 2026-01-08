package com.congestion.tax.repository.charge;

import com.congestion.tax.entity.charge.VehicleDailyChargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDailyChargeRepository extends JpaRepository<VehicleDailyChargeEntity, Long> {
}
