package com.reddot.api.service;

import com.reddot.api.dto.PeriodDto;
import com.reddot.api.model.Period;
import com.reddot.api.model.User;
import com.reddot.api.repository.PeriodRepository;
import com.reddot.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodService {
    
    @Autowired
    private PeriodRepository periodRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Transactional
    public PeriodDto createPeriod(PeriodDto periodDto) {
        User user = getCurrentUser();
        
        Period period = new Period();
        period.setUser(user);
        period.setStartDate(periodDto.getStartDate());
        period.setEndDate(periodDto.getEndDate());
        period.setAverageFlowIntensity(periodDto.getAverageFlowIntensity());
        period.setPainLevel(periodDto.getPainLevel());
        period.setNotes(periodDto.getNotes());
        
        period = periodRepository.save(period);
        
        // Update user's last period start
        if (user.getLastPeriodStart() == null || 
            period.getStartDate().isAfter(user.getLastPeriodStart())) {
            user.setLastPeriodStart(period.getStartDate());
            userRepository.save(user);
        }
        
        return PeriodDto.fromEntity(period);
    }
    
    public List<PeriodDto> getUserPeriods() {
        User user = getCurrentUser();
        return periodRepository.findByUserOrderByStartDateDesc(user)
                .stream()
                .map(PeriodDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<PeriodDto> getPeriodsByDateRange(LocalDate startDate, LocalDate endDate) {
        User user = getCurrentUser();
        return periodRepository.findByUserAndDateRange(user, startDate, endDate)
                .stream()
                .map(PeriodDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    public PeriodDto getPeriodById(Long id) {
        User user = getCurrentUser();
        Period period = periodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Period not found"));
        
        if (!period.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }
        
        return PeriodDto.fromEntity(period);
    }
    
    @Transactional
    public PeriodDto updatePeriod(Long id, PeriodDto periodDto) {
        User user = getCurrentUser();
        Period period = periodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Period not found"));
        
        if (!period.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }
        
        period.setStartDate(periodDto.getStartDate());
        period.setEndDate(periodDto.getEndDate());
        period.setAverageFlowIntensity(periodDto.getAverageFlowIntensity());
        period.setPainLevel(periodDto.getPainLevel());
        period.setNotes(periodDto.getNotes());
        
        period = periodRepository.save(period);
        return PeriodDto.fromEntity(period);
    }
    
    @Transactional
    public void deletePeriod(Long id) {
        User user = getCurrentUser();
        Period period = periodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Period not found"));
        
        if (!period.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }
        
        periodRepository.delete(period);
    }
}

