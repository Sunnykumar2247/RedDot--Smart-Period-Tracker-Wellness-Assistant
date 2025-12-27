package com.reddot.api.controller;

import com.reddot.api.model.Mood;
import com.reddot.api.model.Symptom;
import com.reddot.api.model.WellnessLog;
import com.reddot.api.service.WellnessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wellness")
@Tag(name = "Wellness", description = "Wellness tracking endpoints")
public class WellnessController {
    
    @Autowired
    private WellnessService wellnessService;
    
    @PostMapping("/log")
    @Operation(summary = "Log wellness data", description = "Log daily wellness metrics")
    public ResponseEntity<WellnessLog> logWellness(@RequestBody WellnessLog log) {
        WellnessLog saved = wellnessService.logWellness(log);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/logs")
    @Operation(summary = "Get wellness logs", description = "Get wellness logs for date range")
    public ResponseEntity<List<WellnessLog>> getWellnessLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<WellnessLog> logs = wellnessService.getWellnessLogs(startDate, endDate);
        return ResponseEntity.ok(logs);
    }
    
    @PostMapping("/symptoms")
    @Operation(summary = "Log symptom", description = "Log a symptom")
    public ResponseEntity<Symptom> logSymptom(@RequestBody Symptom symptom) {
        Symptom saved = wellnessService.logSymptom(symptom);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/symptoms")
    @Operation(summary = "Get symptoms", description = "Get logged symptoms")
    public ResponseEntity<List<Symptom>> getSymptoms(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Symptom> symptoms = wellnessService.getSymptoms(startDate, endDate);
        return ResponseEntity.ok(symptoms);
    }
    
    @PostMapping("/moods")
    @Operation(summary = "Log mood", description = "Log a mood")
    public ResponseEntity<Mood> logMood(@RequestBody Mood mood) {
        Mood saved = wellnessService.logMood(mood);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/moods")
    @Operation(summary = "Get moods", description = "Get logged moods")
    public ResponseEntity<List<Mood>> getMoods(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Mood> moods = wellnessService.getMoods(startDate, endDate);
        return ResponseEntity.ok(moods);
    }
    
    @GetMapping("/tip")
    @Operation(summary = "Get wellness tip", description = "Get a daily wellness tip")
    public ResponseEntity<String> getWellnessTip() {
        String tip = wellnessService.getWellnessTip();
        return ResponseEntity.ok(tip);
    }
}

