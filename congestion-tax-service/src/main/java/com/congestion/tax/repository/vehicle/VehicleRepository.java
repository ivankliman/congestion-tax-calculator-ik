package com.congestion.tax.repository.vehicle;

import com.congestion.tax.entity.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
