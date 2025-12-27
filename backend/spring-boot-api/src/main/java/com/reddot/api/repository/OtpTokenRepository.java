package com.reddot.api.repository;

import com.reddot.api.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByEmailAndOtpAndUsedFalse(String email, String otp);
    Optional<OtpToken> findByEmailAndTypeAndUsedFalse(String email, OtpToken.OtpType type);
}

