package com.congestion.tax.controller.vehicle;

import com.congestion.tax.model.vehicle.VehiclePass;
import com.congestion.tax.model.vehicle.VehiclePassRequest;
import com.congestion.tax.service.vehicle.VehiclePassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehiclePassController {
    private final VehiclePassService vehiclePassService;

    @PostMapping
    public ResponseEntity<VehiclePass> handleVehiclePass(@RequestBody VehiclePassRequest vehiclePassRequest) {
        return ResponseEntity.ok(vehiclePassService.handleVehiclePass(vehiclePassRequest));
    }
}
