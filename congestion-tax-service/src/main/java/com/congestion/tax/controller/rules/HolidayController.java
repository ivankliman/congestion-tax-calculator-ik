package com.congestion.tax.controller.rules;

import com.congestion.tax.model.rules.Holiday;
import com.congestion.tax.service.rules.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holidays")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;

    @PostMapping
    public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.createHoliday(holiday));
    }

    @PutMapping
    public ResponseEntity<Holiday> updateHoliday(@RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.updateHoliday(holiday));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Holiday> getHoliday(@PathVariable Long id) {
        return ResponseEntity.ok(holidayService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Holiday>> getHolidays(Pageable pageable) {
        return ResponseEntity.ok(holidayService.findHolidays(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
