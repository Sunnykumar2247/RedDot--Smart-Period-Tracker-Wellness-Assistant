package com.reddot.api.service;

import com.reddot.api.dto.*;
import com.reddot.api.model.OtpToken;
import com.reddot.api.model.User;
import com.reddot.api.repository.OtpTokenRepository;
import com.reddot.api.repository.UserRepository;
import com.reddot.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private OtpTokenRepository otpTokenRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Transactional
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setConsentGiven(request.getConsentGiven());
        user.setConsentDate(request.getConsentGiven() ? LocalDateTime.now() : null);
        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerificationExpiry(LocalDateTime.now().plusDays(1));
        user.setActive(true);
        
        user = userRepository.save(user);
        
        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), user.getEmailVerificationToken());
        
        // Auto-login after signup
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        return generateAuthResponse(authentication);
    }
    
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        return generateAuthResponse(authentication);
    }
    
    private AuthResponse generateAuthResponse(Authentication authentication) {
        String accessToken = tokenProvider.generateToken(authentication);
        String username = authentication.getName();
        String refreshToken = tokenProvider.generateRefreshToken(username);
        
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserProfileDto userProfile = mapToUserProfileDto(user);
        
        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(tokenProvider.getJwtExpiration() / 1000); // Convert to seconds
        response.setUser(userProfile);
        
        return response;
    }
    
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        
        String otp = generateOtp();
        OtpToken otpToken = new OtpToken();
        otpToken.setEmail(email);
        otpToken.setOtp(otp);
        otpToken.setType(OtpToken.OtpType.PASSWORD_RESET);
        otpToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));
        otpToken.setUsed(false);
        
        otpTokenRepository.save(otpToken);
        
        emailService.sendPasswordResetOtp(email, otp);
    }
    
    public void resetPassword(String email, String otp, String newPassword) {
        OtpToken otpToken = otpTokenRepository.findByEmailAndOtpAndUsedFalse(email, otp)
                .orElseThrow(() -> new RuntimeException("Invalid or expired OTP"));
        
        if (otpToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        otpToken.setUsed(true);
        otpTokenRepository.save(otpToken);
    }
    
    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));
        
        if (user.getEmailVerificationExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token has expired");
        }
        
        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
    }
    
    public AuthResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        
        String username = tokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(), null, Collections.emptyList()
        );
        
        return generateAuthResponse(authentication);
    }
    
    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
    
    private UserProfileDto mapToUserProfileDto(User user) {
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

