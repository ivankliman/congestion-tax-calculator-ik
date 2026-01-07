package com.congestion.tax.controller.rules;

import com.congestion.tax.model.rules.CongestionTaxExemption;
import com.congestion.tax.service.rules.CongestionTaxExemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/congestion-tax-exemptions")
@RequiredArgsConstructor
public class CongestionTaxExemptionController {
    private final CongestionTaxExemptionService congestionTaxExemptionService;

    @PostMapping
    public ResponseEntity<CongestionTaxExemption> createCongestionTaxExemption(@RequestBody CongestionTaxExemption congestionTaxExemption) {
        return ResponseEntity.ok(congestionTaxExemptionService.createCongestionTaxExemption(congestionTaxExemption));
    }

    @PutMapping
    public ResponseEntity<CongestionTaxExemption> updateCongestionTaxExemption(@RequestBody CongestionTaxExemption congestionTaxExemption) {
        return ResponseEntity.ok(congestionTaxExemptionService.updateCongestionTaxExemption(congestionTaxExemption));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongestionTaxExemption> getCongestionTaxExemption(@PathVariable Long id) {
        return ResponseEntity.ok(congestionTaxExemptionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CongestionTaxExemption>> getCongestionTaxExemptions(Pageable pageable) {
        return ResponseEntity.ok(congestionTaxExemptionService.findCongestionTaxExemptionRecords(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCongestionTaxExemption(@PathVariable Long id) {
        congestionTaxExemptionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
