package com.reddot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String otp;
    
    @Enumerated(EnumType.STRING)
    private OtpType type;
    
    @Column(nullable = false)
    private LocalDateTime expiryTime;
    
    private Boolean used = false;
    
    public enum OtpType {
        PASSWORD_RESET, EMAIL_VERIFICATION
    }
}

