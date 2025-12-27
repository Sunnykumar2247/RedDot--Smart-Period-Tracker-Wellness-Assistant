package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "moods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mood {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private MoodType moodType;
    
    private Integer intensity; // 1-5 scale
    
    @Column(length = 500)
    private String notes;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public enum MoodType {
        HAPPY, SAD, ANXIOUS, IRRITABLE, CALM, ENERGETIC, TIRED, STRESSED
    }
}

