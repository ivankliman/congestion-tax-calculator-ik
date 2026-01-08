package com.congestion.tax.mapper.charge;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.charge.CongestionTaxPendingChargeEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface CongestionTaxPendingChargeRepositoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CongestionTaxPendingChargeEntity toEntity(CityEntity city, VehicleEntity vehicle, Instant firstPass, Instant lastPass, Double price);
}
