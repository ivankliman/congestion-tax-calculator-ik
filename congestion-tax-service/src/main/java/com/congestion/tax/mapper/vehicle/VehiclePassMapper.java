package com.congestion.tax.mapper.vehicle;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import com.congestion.tax.entity.vehicle.VehiclePassEntity;
import com.congestion.tax.mapper.AbstractMapper;
import com.congestion.tax.model.vehicle.VehiclePass;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface VehiclePassMapper extends AbstractMapper<VehiclePass, VehiclePassEntity> {
    VehiclePassEntity toEntity(VehicleEntity vehicleEntity, CityEntity cityEntity, LocalDateTime vehiclePassDate);
}
