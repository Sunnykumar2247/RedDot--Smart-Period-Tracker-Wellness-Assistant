package com.reddot.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer age;
    private Double height;
    private Double weight;
    private Integer averageCycleLength;
    private Integer averagePeriodLength;
    private LocalDate lastPeriodStart;
    private Set<String> healthConditions;
    private String activityLevel;
    private String dietType;
    private Boolean consentGiven;
    private Boolean dataSharingEnabled;
    private Boolean anonymousMode;
    private String role;
    private Boolean emailVerified;
}

