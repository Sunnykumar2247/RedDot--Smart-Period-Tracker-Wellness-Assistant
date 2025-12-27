package com.reddot.api.service;

import com.reddot.api.model.*;
import com.reddot.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    
    @Autowired
    private PeriodRepository periodRepository;
    
    @Autowired
    private SymptomRepository symptomRepository;
    
    @Autowired
    private MoodRepository moodRepository;
    
    @Autowired
    private WellnessLogRepository wellnessLogRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public Map<String, Object> getCycleConsistency() {
        User user = getCurrentUser();
        List<Period> periods = periodRepository.findByUserOrderByStartDateDesc(user);
        
        Map<String, Object> result = new HashMap<>();
        
        if (periods.size() < 2) {
            result.put("averageCycleLength", user.getAverageCycleLength() != null ? user.getAverageCycleLength() : 28);
            result.put("consistency", "insufficient_data");
            result.put("cycleLengths", new ArrayList<>());
            return result;
        }
        
        List<Integer> cycleLengths = new ArrayList<>();
        for (int i = 0; i < periods.size() - 1; i++) {
            LocalDate current = periods.get(i).getStartDate();
            LocalDate next = periods.get(i + 1).getStartDate();
            long days = ChronoUnit.DAYS.between(next, current);
            if (days > 0 && days < 45) {
                cycleLengths.add((int) days);
            }
        }
        
        if (cycleLengths.isEmpty()) {
            result.put("averageCycleLength", 28);
            result.put("consistency", "insufficient_data");
            result.put("cycleLengths", new ArrayList<>());
            return result;
        }
        
        double avg = cycleLengths.stream().mapToInt(Integer::intValue).average().orElse(28);
        double variance = cycleLengths.stream()
                .mapToDouble(length -> Math.pow(length - avg, 2))
                .average().orElse(0);
        double stdDev = Math.sqrt(variance);
        
        String consistency;
        if (stdDev < 3) {
            consistency = "very_regular";
        } else if (stdDev < 7) {
            consistency = "regular";
        } else {
            consistency = "irregular";
        }
        
        result.put("averageCycleLength", (int) avg);
        result.put("standardDeviation", Math.round(stdDev * 10.0) / 10.0);
        result.put("consistency", consistency);
        result.put("cycleLengths", cycleLengths);
        result.put("totalCycles", cycleLengths.size());
        
        return result;
    }
    
    public Map<String, Object> getSymptomFrequency() {
        User user = getCurrentUser();
        List<Symptom> symptoms = symptomRepository.findByUserOrderByDateDesc(user);
        
        Map<String, Long> frequency = symptoms.stream()
                .collect(Collectors.groupingBy(Symptom::getSymptomType, Collectors.counting()));
        
        Map<String, Object> result = new HashMap<>();
        result.put("frequency", frequency);
        result.put("totalSymptoms", symptoms.size());
        result.put("uniqueSymptoms", frequency.size());
        
        return result;
    }
    
    public Map<String, Object> getMoodTrends() {
        User user = getCurrentUser();
        List<Mood> moods = moodRepository.findByUserOrderByDateDesc(user);
        
        Map<String, Long> moodCounts = moods.stream()
                .collect(Collectors.groupingBy(m -> m.getMoodType().name(), Collectors.counting()));
        
        Map<String, Object> result = new HashMap<>();
        result.put("moodDistribution", moodCounts);
        result.put("totalMoods", moods.size());
        result.put("averageIntensity", moods.stream()
                .mapToInt(Mood::getIntensity)
                .average()
                .orElse(0));
        
        return result;
    }
    
    public Map<String, Object> getWellnessScore() {
        User user = getCurrentUser();
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<WellnessLog> logs = wellnessLogRepository.findByUserAndDateRange(user, thirtyDaysAgo, LocalDate.now());
        
        double score = 50.0; // Base score
        
        if (!logs.isEmpty()) {
            double avgWater = logs.stream()
                    .filter(l -> l.getWaterIntake() != null)
                    .mapToInt(WellnessLog::getWaterIntake)
                    .average().orElse(0);
            if (avgWater >= 2000) score += 15;
            else if (avgWater >= 1500) score += 10;
            
            double avgSleep = logs.stream()
                    .filter(l -> l.getSleepHours() != null)
                    .mapToInt(WellnessLog::getSleepHours)
                    .average().orElse(0);
            if (avgSleep >= 7 && avgSleep <= 9) score += 20;
            else if (avgSleep >= 6) score += 10;
            
            double avgExercise = logs.stream()
                    .filter(l -> l.getExerciseMinutes() != null)
                    .mapToInt(WellnessLog::getExerciseMinutes)
                    .average().orElse(0);
            if (avgExercise >= 150) score += 15;
            else if (avgExercise >= 75) score += 10;
        }
        
        score = Math.min(100, Math.max(0, score));
        
        Map<String, Object> result = new HashMap<>();
        result.put("score", Math.round(score));
        result.put("level", getWellnessLevel(score));
        result.put("logsCount", logs.size());
        
        return result;
    }
    
    private String getWellnessLevel(double score) {
        if (score >= 80) return "excellent";
        if (score >= 60) return "good";
        if (score >= 40) return "fair";
        return "needs_improvement";
    }
    
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("cycleConsistency", getCycleConsistency());
        dashboard.put("symptomFrequency", getSymptomFrequency());
        dashboard.put("moodTrends", getMoodTrends());
        dashboard.put("wellnessScore", getWellnessScore());
        return dashboard;
    }
}

