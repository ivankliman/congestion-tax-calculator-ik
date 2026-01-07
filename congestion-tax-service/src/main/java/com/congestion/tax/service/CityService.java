package com.congestion.tax.service;

import com.congestion.tax.exception.IdMissingException;
import com.congestion.tax.exception.NotFoundException;
import com.congestion.tax.mapper.CityMapper;
import com.congestion.tax.model.City;
import com.congestion.tax.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
/*
 Service layer for the cities. We could potentially add multiple filtering methods, but that is to do
 */
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public City createCity(City city) {
        final var recordToSave = cityMapper.toEntity(city);
        final var savedRecord = cityRepository.save(recordToSave);

        return cityMapper.toDto(savedRecord);
    }

    public City updateCity(City city) {
        //field validation -- potentially move to javax/jakarta validation
        if (Objects.isNull(city.id())) {
            throw new IdMissingException("Id is required for the edit request");
        }
        final var recordToUpdate = cityRepository.findById(city.id());
        if(recordToUpdate.isEmpty()){
            throw new NotFoundException("No record found with id " + city.id());
        }

        final var recordToSave = cityMapper.toEntity(city);
        final var savedRecord = cityRepository.save(recordToSave);

        return cityMapper.toDto(savedRecord);
    }

    public City getById(Long id) {
        final var requestedRecord = cityRepository.findById(id);
        if(requestedRecord.isEmpty()){
            throw new NotFoundException("No record found with id " + id);
        }

        return cityMapper.toDto(requestedRecord.get());
    }

    public Page<City> findCities(Pageable pageable) {
        return cityRepository
                .findAll(pageable).map(cityMapper::toDto);
    }

    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
