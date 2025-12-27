package com.reddot.api.controller;

import com.reddot.api.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Analytics and dashboard endpoints")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard data", description = "Get all analytics data for dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> data = analyticsService.getDashboardData();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/cycle-consistency")
    @Operation(summary = "Get cycle consistency", description = "Get cycle consistency metrics")
    public ResponseEntity<Map<String, Object>> getCycleConsistency() {
        Map<String, Object> data = analyticsService.getCycleConsistency();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/symptom-frequency")
    @Operation(summary = "Get symptom frequency", description = "Get symptom frequency analysis")
    public ResponseEntity<Map<String, Object>> getSymptomFrequency() {
        Map<String, Object> data = analyticsService.getSymptomFrequency();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/mood-trends")
    @Operation(summary = "Get mood trends", description = "Get mood trend analysis")
    public ResponseEntity<Map<String, Object>> getMoodTrends() {
        Map<String, Object> data = analyticsService.getMoodTrends();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/wellness-score")
    @Operation(summary = "Get wellness score", description = "Get overall wellness score")
    public ResponseEntity<Map<String, Object>> getWellnessScore() {
        Map<String, Object> data = analyticsService.getWellnessScore();
        return ResponseEntity.ok(data);
    }
}

