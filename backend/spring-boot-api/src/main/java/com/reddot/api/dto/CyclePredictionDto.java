package com.reddot.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CyclePredictionDto {
    private LocalDate predictedPeriodStart;
    private LocalDate predictedOvulationDate;
    private LocalDate fertileWindowStart;
    private LocalDate fertileWindowEnd;
    private Double predictionConfidence; // 0.0 - 1.0
    private String explanation; // Human-friendly explanation
    private Boolean isIrregular;
    private Integer estimatedCycleLength;
}

