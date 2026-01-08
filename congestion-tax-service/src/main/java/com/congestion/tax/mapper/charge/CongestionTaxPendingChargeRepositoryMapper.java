package com.congestion.tax.mapper.charge;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.charge.CongestionTaxPendingChargeEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import org.mapstruct.Mapper;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface CongestionTaxPendingChargeRepositoryMapper {
    CongestionTaxPendingChargeEntity toEntity(CityEntity cityEntity, VehicleEntity vehicleEntity, Instant firstPass, Instant lastPass, Double price);
}
