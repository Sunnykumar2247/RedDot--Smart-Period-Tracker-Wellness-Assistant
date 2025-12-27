package com.reddot.api.controller;

import com.reddot.api.dto.PeriodDto;
import com.reddot.api.service.PeriodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/periods")
@Tag(name = "Period Tracking", description = "Period and cycle tracking endpoints")
public class PeriodController {
    
    @Autowired
    private PeriodService periodService;
    
    @PostMapping
    @Operation(summary = "Create period", description = "Log a new period")
    public ResponseEntity<PeriodDto> createPeriod(@Valid @RequestBody PeriodDto periodDto) {
        PeriodDto created = periodService.createPeriod(periodDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    @Operation(summary = "Get all periods", description = "Get all periods for the current user")
    public ResponseEntity<List<PeriodDto>> getAllPeriods() {
        List<PeriodDto> periods = periodService.getUserPeriods();
        return ResponseEntity.ok(periods);
    }
    
    @GetMapping("/range")
    @Operation(summary = "Get periods by date range", description = "Get periods within a date range")
    public ResponseEntity<List<PeriodDto>> getPeriodsByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PeriodDto> periods = periodService.getPeriodsByDateRange(startDate, endDate);
        return ResponseEntity.ok(periods);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get period by ID", description = "Get a specific period")
    public ResponseEntity<PeriodDto> getPeriod(@PathVariable Long id) {
        PeriodDto period = periodService.getPeriodById(id);
        return ResponseEntity.ok(period);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update period", description = "Update an existing period")
    public ResponseEntity<PeriodDto> updatePeriod(@PathVariable Long id, 
                                                 @Valid @RequestBody PeriodDto periodDto) {
        PeriodDto updated = periodService.updatePeriod(id, periodDto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete period", description = "Delete a period")
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        periodService.deletePeriod(id);
        return ResponseEntity.noContent().build();
    }
}

