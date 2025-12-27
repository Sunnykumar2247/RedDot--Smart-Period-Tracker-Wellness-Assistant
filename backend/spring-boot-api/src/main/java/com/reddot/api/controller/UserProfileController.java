package com.reddot.api.controller;

import com.reddot.api.dto.UserProfileDto;
import com.reddot.api.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "User Profile", description = "User profile management endpoints")
public class UserProfileController {
    
    @Autowired
    private UserProfileService profileService;
    
    @GetMapping
    @Operation(summary = "Get user profile", description = "Get current user's profile")
    public ResponseEntity<UserProfileDto> getProfile() {
        UserProfileDto profile = profileService.getProfile();
        return ResponseEntity.ok(profile);
    }
    
    @PutMapping
    @Operation(summary = "Update user profile", description = "Update current user's profile")
    public ResponseEntity<UserProfileDto> updateProfile(@Valid @RequestBody UserProfileDto profileDto) {
        UserProfileDto updated = profileService.updateProfile(profileDto);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/onboarding")
    @Operation(summary = "Complete onboarding", description = "Complete user onboarding process")
    public ResponseEntity<UserProfileDto> completeOnboarding(@Valid @RequestBody UserProfileDto profileDto) {
        profileService.completeOnboarding(profileDto);
        UserProfileDto profile = profileService.getProfile();
        return ResponseEntity.ok(profile);
    }
}

