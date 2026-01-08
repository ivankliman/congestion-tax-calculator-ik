package com.congestion.tax.mapper.vehicle;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import com.congestion.tax.entity.vehicle.VehiclePassEntity;
import com.congestion.tax.mapper.AbstractMapper;
import com.congestion.tax.model.vehicle.VehiclePass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface VehiclePassMapper extends AbstractMapper<VehiclePass, VehiclePassEntity> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VehiclePassEntity toEntity(VehicleEntity vehicle, CityEntity city, LocalDateTime vehiclePassDate);
}
