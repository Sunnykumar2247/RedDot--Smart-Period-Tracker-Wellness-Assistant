package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    private LocalDate dateOfBirth;
    
    private Integer age;
    
    private Double height; // in cm
    private Double weight; // in kg
    
    // Cycle information
    private Integer averageCycleLength; // days
    private Integer averagePeriodLength; // days
    private LocalDate lastPeriodStart;
    
    // Health conditions
    @ElementCollection
    @CollectionTable(name = "user_health_conditions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "condition")
    private Set<String> healthConditions = new HashSet<>(); // PCOS, Thyroid, etc.
    
    // Lifestyle
    private String activityLevel; // SEDENTARY, MODERATE, ACTIVE
    private String dietType; // VEGETARIAN, VEGAN, OMNIVORE, etc.
    
    // Privacy & Consent
    private Boolean consentGiven = false;
    private LocalDateTime consentDate;
    private Boolean dataSharingEnabled = false;
    private Boolean anonymousMode = false;
    
    // Role
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    // Account status
    private Boolean emailVerified = false;
    private String emailVerificationToken;
    private LocalDateTime emailVerificationExpiry;
    
    private Boolean active = true;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public enum Role {
        USER, ADMIN, DOCTOR
    }
}

