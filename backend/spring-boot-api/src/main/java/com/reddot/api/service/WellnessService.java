package com.reddot.api.service;

import com.reddot.api.model.Mood;
import com.reddot.api.model.Symptom;
import com.reddot.api.model.User;
import com.reddot.api.model.WellnessLog;
import com.reddot.api.repository.MoodRepository;
import com.reddot.api.repository.SymptomRepository;
import com.reddot.api.repository.UserRepository;
import com.reddot.api.repository.WellnessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WellnessService {
    
    @Autowired
    private WellnessLogRepository wellnessLogRepository;
    
    @Autowired
    private SymptomRepository symptomRepository;
    
    @Autowired
    private MoodRepository moodRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Transactional
    public WellnessLog logWellness(WellnessLog log) {
        User user = getCurrentUser();
        log.setUser(user);
        if (log.getDate() == null) {
            log.setDate(LocalDate.now());
        }
        return wellnessLogRepository.save(log);
    }
    
    public List<WellnessLog> getWellnessLogs(LocalDate startDate, LocalDate endDate) {
        User user = getCurrentUser();
        if (startDate != null && endDate != null) {
            return wellnessLogRepository.findByUserAndDateRange(user, startDate, endDate);
        }
        return wellnessLogRepository.findByUserOrderByDateDesc(user);
    }
    
    @Transactional
    public Symptom logSymptom(Symptom symptom) {
        User user = getCurrentUser();
        symptom.setUser(user);
        if (symptom.getDate() == null) {
            symptom.setDate(LocalDate.now());
        }
        return symptomRepository.save(symptom);
    }
    
    public List<Symptom> getSymptoms(LocalDate startDate, LocalDate endDate) {
        User user = getCurrentUser();
        if (startDate != null && endDate != null) {
            return symptomRepository.findByUserAndDateRange(user, startDate, endDate);
        }
        return symptomRepository.findByUserOrderByDateDesc(user);
    }
    
    @Transactional
    public Mood logMood(Mood mood) {
        User user = getCurrentUser();
        mood.setUser(user);
        if (mood.getDate() == null) {
            mood.setDate(LocalDate.now());
        }
        return moodRepository.save(mood);
    }
    
    public List<Mood> getMoods(LocalDate startDate, LocalDate endDate) {
        User user = getCurrentUser();
        if (startDate != null && endDate != null) {
            return moodRepository.findByUserAndDateRange(user, startDate, endDate);
        }
        return moodRepository.findByUserOrderByDateDesc(user);
    }
    
    public String getWellnessTip() {
        // Simple rule-based wellness tips
        String[] tips = {
            "Stay hydrated! Aim for 8-10 glasses of water daily.",
            "Get 7-9 hours of quality sleep each night.",
            "Light exercise like walking can help with period cramps.",
            "Eat iron-rich foods during your period to combat fatigue.",
            "Practice deep breathing exercises to manage stress.",
            "Magnesium-rich foods can help reduce bloating.",
            "Yoga and stretching can ease menstrual discomfort.",
            "Limit caffeine intake if you experience breast tenderness.",
            "Track your symptoms to identify patterns.",
            "Remember, it's normal for cycles to vary slightly."
        };
        return tips[(int) (Math.random() * tips.length)];
    }
}

