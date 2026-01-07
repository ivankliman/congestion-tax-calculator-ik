package com.congestion.tax.mapper;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.model.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper extends AbstractMapper<City, CityEntity> {
}
