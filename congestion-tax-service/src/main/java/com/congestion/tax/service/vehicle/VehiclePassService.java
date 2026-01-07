package com.congestion.tax.service.vehicle;

import com.congestion.tax.mapper.vehicle.VehiclePassMapper;
import com.congestion.tax.model.vehicle.VehiclePass;
import com.congestion.tax.repository.vehicle.VehiclePassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiclePassService {
    private final VehiclePassRepository vehiclePassRepository;
    private final VehiclePassMapper vehiclePassMapper;

    public VehiclePass createVehiclePass(VehiclePass vehiclePass) {
        final var recordToSave = vehiclePassMapper.toEntity(vehiclePass);
        final var savedRecord = vehiclePassRepository.save(recordToSave);

        return vehiclePassMapper.toDto(savedRecord);
    }
}
