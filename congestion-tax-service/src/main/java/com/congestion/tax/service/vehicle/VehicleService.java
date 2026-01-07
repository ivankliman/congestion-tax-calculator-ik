package com.congestion.tax.service.vehicle;

import com.congestion.tax.exception.IdMissingException;
import com.congestion.tax.exception.NotFoundException;
import com.congestion.tax.mapper.vehicle.VehicleMapper;
import com.congestion.tax.model.vehicle.Vehicle;
import com.congestion.tax.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public Vehicle createVehicle(Vehicle vehicle) {
        final var recordToSave = vehicleMapper.toEntity(vehicle);
        final var savedRecord = vehicleRepository.save(recordToSave);

        return vehicleMapper.toDto(savedRecord);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        //field validation -- potentially move to javax/jakarta validation
        if (Objects.isNull(vehicle.id())) {
            throw new IdMissingException("Id is required for the edit request");
        }
        final var recordToUpdate = vehicleRepository.findById(vehicle.id());
        if(recordToUpdate.isEmpty()){
            throw new NotFoundException("No record found with id " + vehicle.id());
        }

        final var recordToSave = vehicleMapper.toEntity(vehicle);
        final var savedRecord = vehicleRepository.save(recordToSave);

        return vehicleMapper.toDto(savedRecord);
    }

    public Vehicle getById(Long id) {
        final var requestedRecord = vehicleRepository.findById(id);
        if(requestedRecord.isEmpty()){
            throw new NotFoundException("No record found with id " + id);
        }

        return vehicleMapper.toDto(requestedRecord.get());
    }

    public Page<Vehicle> findVehicles(Pageable pageable) {
        return vehicleRepository
                .findAll(pageable).map(vehicleMapper::toDto);
    }

    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }
}
