package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cycles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cycle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDate cycleStartDate;
    
    private LocalDate cycleEndDate;
    
    private Integer cycleLength; // days
    
    private Integer periodLength; // days
    
    private LocalDate predictedPeriodStart;
    
    private LocalDate predictedOvulationDate;
    
    private LocalDate fertileWindowStart;
    private LocalDate fertileWindowEnd;
    
    private Boolean isIrregular = false;
    
    private Double predictionConfidence; // 0.0 - 1.0
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

