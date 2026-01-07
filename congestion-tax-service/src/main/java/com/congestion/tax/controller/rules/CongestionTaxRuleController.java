package com.congestion.tax.controller.rules;

import com.congestion.tax.model.rules.CongestionTaxRule;
import com.congestion.tax.service.rules.CongestionTaxRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/congestion-tax-rules")
@RequiredArgsConstructor
public class CongestionTaxRuleController {
    private final CongestionTaxRuleService congestionTaxRuleService;

    @PostMapping
    public ResponseEntity<CongestionTaxRule> createCongestionTaxRule(@RequestBody CongestionTaxRule congestionTaxRule) {
        return ResponseEntity.ok(congestionTaxRuleService.createCongestionTaxRule(congestionTaxRule));
    }

    @PutMapping
    public ResponseEntity<CongestionTaxRule> updateCongestionTaxRule(@RequestBody CongestionTaxRule congestionTaxRule) {
        return ResponseEntity.ok(congestionTaxRuleService.updateCongestionTaxRule(congestionTaxRule));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongestionTaxRule> getCongestionTaxRule(@PathVariable Long id) {
        return ResponseEntity.ok(congestionTaxRuleService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CongestionTaxRule>> getCongestionTaxRules(Pageable pageable) {
        return ResponseEntity.ok(congestionTaxRuleService.findCongestionTaxRules(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCongestionTaxRule(@PathVariable Long id) {
        congestionTaxRuleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
