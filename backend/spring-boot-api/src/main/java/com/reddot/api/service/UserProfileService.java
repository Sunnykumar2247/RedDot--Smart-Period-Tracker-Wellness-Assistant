package com.reddot.api.service;

import com.reddot.api.dto.UserProfileDto;
import com.reddot.api.model.User;
import com.reddot.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserProfileService {
    
    @Autowired
    private UserRepository userRepository;
    
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public UserProfileDto getProfile() {
        User user = getCurrentUser();
        return mapToDto(user);
    }
    
    @Transactional
    public UserProfileDto updateProfile(UserProfileDto profileDto) {
        User user = getCurrentUser();
        
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setDateOfBirth(profileDto.getDateOfBirth());
        
        // Calculate age from date of birth
        if (profileDto.getDateOfBirth() != null) {
            int age = Period.between(profileDto.getDateOfBirth(), LocalDate.now()).getYears();
            user.setAge(age);
        }
        
        user.setHeight(profileDto.getHeight());
        user.setWeight(profileDto.getWeight());
        user.setAverageCycleLength(profileDto.getAverageCycleLength());
        user.setAveragePeriodLength(profileDto.getAveragePeriodLength());
        user.setLastPeriodStart(profileDto.getLastPeriodStart());
        user.setHealthConditions(profileDto.getHealthConditions() != null ? 
            profileDto.getHealthConditions() : new HashSet<>());
        user.setActivityLevel(profileDto.getActivityLevel());
        user.setDietType(profileDto.getDietType());
        user.setDataSharingEnabled(profileDto.getDataSharingEnabled());
        user.setAnonymousMode(profileDto.getAnonymousMode());
        
        // Update consent if given
        if (profileDto.getConsentGiven() != null && profileDto.getConsentGiven() && !user.getConsentGiven()) {
            user.setConsentGiven(true);
            user.setConsentDate(java.time.LocalDateTime.now());
        }
        
        user = userRepository.save(user);
        return mapToDto(user);
    }
    
    @Transactional
    public void completeOnboarding(UserProfileDto profileDto) {
        User user = getCurrentUser();
        
        // Set all onboarding fields
        updateProfile(profileDto);
        
        // Ensure consent is given
        if (!user.getConsentGiven()) {
            user.setConsentGiven(true);
            user.setConsentDate(java.time.LocalDateTime.now());
            userRepository.save(user);
        }
    }
    
    private UserProfileDto mapToDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAge(user.getAge());
        dto.setHeight(user.getHeight());
        dto.setWeight(user.getWeight());
        dto.setAverageCycleLength(user.getAverageCycleLength());
        dto.setAveragePeriodLength(user.getAveragePeriodLength());
        dto.setLastPeriodStart(user.getLastPeriodStart());
        dto.setHealthConditions(user.getHealthConditions());
        dto.setActivityLevel(user.getActivityLevel());
        dto.setDietType(user.getDietType());
        dto.setConsentGiven(user.getConsentGiven());
        dto.setDataSharingEnabled(user.getDataSharingEnabled());
        dto.setAnonymousMode(user.getAnonymousMode());
        dto.setRole(user.getRole().name());
        dto.setEmailVerified(user.getEmailVerified());
        return dto;
    }
}

