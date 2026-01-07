package com.congestion.tax.controller;

import com.congestion.tax.model.City;
import com.congestion.tax.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.createCity(city));
    }

    @PutMapping
    public ResponseEntity<City> updateCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.updateCity(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<City>> getCities(Pageable pageable) {
        return ResponseEntity.ok(cityService.findCities(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
