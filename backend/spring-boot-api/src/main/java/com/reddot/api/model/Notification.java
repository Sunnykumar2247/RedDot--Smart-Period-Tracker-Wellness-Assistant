package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String message;
    
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    
    private Boolean read = false;
    
    private LocalDateTime scheduledTime;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public enum NotificationType {
        PERIOD_REMINDER, OVULATION_ALERT, MEDICATION_REMINDER, 
        WELLNESS_TIP, CYCLE_UPDATE, GENERAL
    }
}

