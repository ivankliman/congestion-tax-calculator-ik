package com.congestion.tax.service.vehicle;

import com.congestion.tax.mapper.vehicle.VehiclePassMapper;
import com.congestion.tax.model.vehicle.VehiclePass;
import com.congestion.tax.model.vehicle.VehiclePassRequest;
import com.congestion.tax.repository.CityRepository;
import com.congestion.tax.repository.vehicle.VehiclePassRepository;
import com.congestion.tax.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehiclePassService {
    private final VehiclePassRepository vehiclePassRepository;
    private final VehiclePassMapper vehiclePassMapper;
    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public VehiclePass createVehiclePass(VehiclePassRequest vehiclePassRequest) {
        final var vehicleEntity = vehicleRepository
                .findByLicensePlate(vehiclePassRequest.licensePlate())
                .orElseThrow();

        final var cityEntity = cityRepository
                .findById(vehiclePassRequest.cityId())
                .orElseThrow();

        final var recordToSave = vehiclePassMapper
                .toEntity(vehicleEntity, cityEntity, vehiclePassRequest.vehiclePassedAt());

        //TODO: check if the vehicle should be charged and then add it to the congestion tax pending charge table
        final var savedRecord = vehiclePassRepository.save(recordToSave);

        return vehiclePassMapper.toDto(savedRecord);
    }
}
