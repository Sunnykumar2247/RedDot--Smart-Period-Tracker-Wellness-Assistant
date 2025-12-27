package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "symptoms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symptom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private String symptomType; // CRAMPS, BLOATING, HEADACHE, MOOD_SWINGS, etc.
    
    private Integer severity; // 1-5 scale
    
    @Column(length = 500)
    private String notes;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}

