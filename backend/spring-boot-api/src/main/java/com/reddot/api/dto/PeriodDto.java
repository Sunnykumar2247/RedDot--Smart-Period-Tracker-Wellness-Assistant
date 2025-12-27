package com.reddot.api.dto;

import com.reddot.api.model.Period;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Period.FlowIntensity averageFlowIntensity;
    private Integer painLevel;
    private String notes;
    
    public static PeriodDto fromEntity(Period period) {
        PeriodDto dto = new PeriodDto();
        dto.setId(period.getId());
        dto.setStartDate(period.getStartDate());
        dto.setEndDate(period.getEndDate());
        dto.setAverageFlowIntensity(period.getAverageFlowIntensity());
        dto.setPainLevel(period.getPainLevel());
        dto.setNotes(period.getNotes());
        return dto;
    }
}

