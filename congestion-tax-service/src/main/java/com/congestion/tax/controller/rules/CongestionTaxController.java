package com.congestion.tax.controller.rules;

import com.congestion.tax.model.rules.CongestionTax;
import com.congestion.tax.service.rules.CongestionTaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/congestion-taxes")
@RequiredArgsConstructor
public class CongestionTaxController {
    private final CongestionTaxService congestionTaxService;

    @PostMapping
    public ResponseEntity<CongestionTax> createCongestionTax(@RequestBody CongestionTax congestionTax) {
        return ResponseEntity.ok(congestionTaxService.createCongestionTax(congestionTax));
    }

    @PutMapping
    public ResponseEntity<CongestionTax> updateCongestionTax(@RequestBody CongestionTax congestionTax) {
        return ResponseEntity.ok(congestionTaxService.updateCongestionTax(congestionTax));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongestionTax> getCongestionTax(@PathVariable Long id) {
        return ResponseEntity.ok(congestionTaxService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CongestionTax>> getCongestionTaxes(Pageable pageable) {
        return ResponseEntity.ok(congestionTaxService.findCongestionTaxRecords(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCongestionTax(@PathVariable Long id) {
        congestionTaxService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
