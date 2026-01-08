package com.congestion.tax.repository.vehicle;

import com.congestion.tax.entity.vehicle.VehiclePassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclePassRepository extends JpaRepository<VehiclePassEntity, Long> {
}
