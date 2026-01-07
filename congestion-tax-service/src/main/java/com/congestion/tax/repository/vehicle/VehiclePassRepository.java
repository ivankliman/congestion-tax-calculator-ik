package com.congestion.tax.repository.vehicle;

import com.congestion.tax.entity.vehicle.VehiclePassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclePassRepository extends JpaRepository<VehiclePassEntity, Long> {
}
