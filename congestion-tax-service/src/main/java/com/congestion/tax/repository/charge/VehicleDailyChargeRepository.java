package com.congestion.tax.repository.charge;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.charge.VehicleDailyChargeEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VehicleDailyChargeRepository extends JpaRepository<VehicleDailyChargeEntity, Long> {
    Optional<VehicleDailyChargeEntity> findByCityAndVehicleAndLocalDate(
            CityEntity city,
            VehicleEntity vehicle,
            LocalDate localDate
    );
}
