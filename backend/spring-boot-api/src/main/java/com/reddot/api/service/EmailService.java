package com.reddot.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;
    
    public void sendVerificationEmail(String email, String token) {
        if (mailSender == null) {
            System.out.println("Email service not configured. Verification link: " + frontendUrl + "/verify-email?token=" + token);
            return;
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verify your RedDot account");
        message.setText("Please click the link to verify your email: " + frontendUrl + "/verify-email?token=" + token);
        mailSender.send(message);
    }
    
    public void sendPasswordResetOtp(String email, String otp) {
        if (mailSender == null) {
            System.out.println("Email service not configured. Password reset OTP for " + email + ": " + otp);
            return;
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("RedDot - Password Reset OTP");
        message.setText("Your password reset OTP is: " + otp + "\n\nThis OTP will expire in 15 minutes.");
        mailSender.send(message);
    }
}

