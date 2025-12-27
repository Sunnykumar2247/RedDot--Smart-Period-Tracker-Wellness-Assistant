package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "wellness_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellnessLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDate date;
    
    private Integer waterIntake; // ml
    private Integer sleepHours;
    private Integer sleepQuality; // 1-5 scale
    private Integer exerciseMinutes;
    private String exerciseType;
    
    @Column(length = 1000)
    private String notes;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}

