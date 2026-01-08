package com.congestion.tax.mapper.vehicle;

import com.congestion.tax.entity.vehicle.VehicleEntity;
import com.congestion.tax.mapper.AbstractMapper;
import com.congestion.tax.mapper.CityMapper;
import com.congestion.tax.model.vehicle.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CityMapper.class, VehicleMapper.class})
public interface VehicleMapper extends AbstractMapper<Vehicle, VehicleEntity> {
}
