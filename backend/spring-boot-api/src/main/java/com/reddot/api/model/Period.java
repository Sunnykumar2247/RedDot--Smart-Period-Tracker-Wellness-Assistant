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
@Table(name = "periods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Period {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    private FlowIntensity averageFlowIntensity;
    
    private Integer painLevel; // 0-10 scale
    
    @Column(length = 1000)
    private String notes;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public enum FlowIntensity {
        LIGHT, MODERATE, HEAVY, VERY_HEAVY
    }
}

