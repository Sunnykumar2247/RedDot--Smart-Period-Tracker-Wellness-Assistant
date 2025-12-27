package com.reddot.api.controller;

import com.reddot.api.dto.CyclePredictionDto;
import com.reddot.api.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predictions")
@Tag(name = "Predictions", description = "Cycle prediction endpoints")
public class PredictionController {
    
    @Autowired
    private PredictionService predictionService;
    
    @GetMapping("/cycle")
    @Operation(summary = "Get cycle prediction", description = "Get predicted next period, ovulation, and fertile window")
    public ResponseEntity<CyclePredictionDto> getPrediction() {
        CyclePredictionDto prediction = predictionService.getPrediction();
        return ResponseEntity.ok(prediction);
    }
}

