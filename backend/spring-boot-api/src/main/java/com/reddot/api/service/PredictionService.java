package com.reddot.api.service;

import com.reddot.api.dto.CyclePredictionDto;
import com.reddot.api.model.Period;
import com.reddot.api.model.User;
import com.reddot.api.repository.PeriodRepository;
import com.reddot.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PredictionService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PeriodRepository periodRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${prediction.service.url:http://localhost:8001}")
    private String predictionServiceUrl;
    
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public CyclePredictionDto getPrediction() {
        User user = getCurrentUser();
        List<Period> periods = periodRepository.findByUserOrderByStartDateDesc(user);
        
        // If we have enough data, use ML service, otherwise use fallback logic
        if (periods.size() >= 3) {
            try {
                return getMLPrediction(user, periods);
            } catch (Exception e) {
                // Fallback to rule-based prediction
                return getRuleBasedPrediction(user, periods);
            }
        } else {
            return getRuleBasedPrediction(user, periods);
        }
    }
    
    private CyclePredictionDto getMLPrediction(User user, List<Period> periods) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("user_id", user.getId());
        requestData.put("periods", periods.stream()
                .map(p -> Map.of(
                    "start_date", p.getStartDate().toString(),
                    "end_date", p.getEndDate() != null ? p.getEndDate().toString() : null,
                    "cycle_length", calculateCycleLength(periods, p)
                ))
                .collect(Collectors.toList()));
        requestData.put("average_cycle_length", user.getAverageCycleLength());
        requestData.put("average_period_length", user.getAveragePeriodLength());
        
        try {
            Map<String, Object> response = restTemplate.postForObject(
                predictionServiceUrl + "/predict", requestData, Map.class);
            
            CyclePredictionDto dto = new CyclePredictionDto();
            dto.setPredictedPeriodStart(LocalDate.parse((String) response.get("predicted_period_start")));
            dto.setPredictedOvulationDate(LocalDate.parse((String) response.get("predicted_ovulation_date")));
            dto.setFertileWindowStart(LocalDate.parse((String) response.get("fertile_window_start")));
            dto.setFertileWindowEnd(LocalDate.parse((String) response.get("fertile_window_end")));
            dto.setPredictionConfidence((Double) response.get("confidence"));
            dto.setExplanation((String) response.get("explanation"));
            dto.setIsIrregular((Boolean) response.get("is_irregular"));
            dto.setEstimatedCycleLength(((Number) response.get("estimated_cycle_length")).intValue());
            
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Prediction service unavailable, using fallback", e);
        }
    }
    
    private CyclePredictionDto getRuleBasedPrediction(User user, List<Period> periods) {
        CyclePredictionDto dto = new CyclePredictionDto();
        
        LocalDate lastPeriodStart = user.getLastPeriodStart();
        if (lastPeriodStart == null && !periods.isEmpty()) {
            lastPeriodStart = periods.get(0).getStartDate();
        }
        
        if (lastPeriodStart == null) {
            // No period data - use default
            dto.setPredictedPeriodStart(LocalDate.now().plusDays(28));
            dto.setPredictionConfidence(0.3);
            dto.setExplanation("We don't have enough data yet. Please log your periods to get accurate predictions.");
            dto.setEstimatedCycleLength(28);
            return dto;
        }
        
        // Calculate average cycle length from historical data
        int avgCycleLength = calculateAverageCycleLength(periods, user.getAverageCycleLength());
        
        // Predict next period
        LocalDate predictedPeriodStart = lastPeriodStart.plusDays(avgCycleLength);
        dto.setPredictedPeriodStart(predictedPeriodStart);
        dto.setEstimatedCycleLength(avgCycleLength);
        
        // Ovulation typically occurs 14 days before period (can vary 12-16 days)
        LocalDate ovulationDate = predictedPeriodStart.minusDays(14);
        dto.setPredictedOvulationDate(ovulationDate);
        
        // Fertile window: 5 days before ovulation to 1 day after
        dto.setFertileWindowStart(ovulationDate.minusDays(5));
        dto.setFertileWindowEnd(ovulationDate.plusDays(1));
        
        // Calculate confidence based on data quality
        double confidence = calculateConfidence(periods.size(), avgCycleLength);
        dto.setPredictionConfidence(confidence);
        
        // Check for irregularity
        boolean isIrregular = checkIrregularity(periods, avgCycleLength);
        dto.setIsIrregular(isIrregular);
        
        // Generate explanation
        dto.setExplanation(generateExplanation(periods.size(), avgCycleLength, confidence, isIrregular));
        
        return dto;
    }
    
    private int calculateAverageCycleLength(List<Period> periods, Integer userAverage) {
        if (periods.size() < 2) {
            return userAverage != null ? userAverage : 28;
        }
        
        // Calculate cycle lengths from periods
        int totalDays = 0;
        int count = 0;
        
        for (int i = 0; i < periods.size() - 1; i++) {
            LocalDate current = periods.get(i).getStartDate();
            LocalDate next = periods.get(i + 1).getStartDate();
            long days = ChronoUnit.DAYS.between(next, current);
            if (days > 0 && days < 45) { // Filter outliers
                totalDays += (int) days;
                count++;
            }
        }
        
        if (count > 0) {
            int calculated = totalDays / count;
            // Weight with user's average if available
            if (userAverage != null) {
                return (calculated + userAverage) / 2;
            }
            return calculated;
        }
        
        return userAverage != null ? userAverage : 28;
    }
    
    private int calculateCycleLength(List<Period> periods, Period period) {
        int index = periods.indexOf(period);
        if (index < periods.size() - 1) {
            LocalDate current = period.getStartDate();
            LocalDate next = periods.get(index + 1).getStartDate();
            return (int) ChronoUnit.DAYS.between(next, current);
        }
        return 28; // Default
    }
    
    private double calculateConfidence(int periodCount, int cycleLength) {
        // More periods = higher confidence
        // Standard cycle length (26-32 days) = higher confidence
        double dataConfidence = Math.min(0.9, 0.5 + (periodCount * 0.1));
        double cycleConfidence = (cycleLength >= 26 && cycleLength <= 32) ? 0.9 : 0.7;
        return (dataConfidence + cycleConfidence) / 2.0;
    }
    
    private boolean checkIrregularity(List<Period> periods, int avgCycleLength) {
        if (periods.size() < 3) return false;
        
        int variance = 0;
        for (int i = 0; i < periods.size() - 1; i++) {
            LocalDate current = periods.get(i).getStartDate();
            LocalDate next = periods.get(i + 1).getStartDate();
            int cycleLength = (int) ChronoUnit.DAYS.between(next, current);
            variance += Math.abs(cycleLength - avgCycleLength);
        }
        
        int avgVariance = variance / (periods.size() - 1);
        return avgVariance > 7; // More than 7 days variance = irregular
    }
    
    private String generateExplanation(int periodCount, int cycleLength, double confidence, boolean isIrregular) {
        StringBuilder explanation = new StringBuilder();
        
        if (periodCount < 3) {
            explanation.append("We're still learning your cycle pattern. ");
        } else {
            explanation.append("Based on your ").append(periodCount).append(" logged periods, ");
        }
        
        explanation.append("your next period is predicted to start in about ")
                .append(cycleLength).append(" days. ");
        
        if (isIrregular) {
            explanation.append("Your cycles show some variation, which is normal. ");
        }
        
        int confidencePercent = (int) (confidence * 100);
        explanation.append("Prediction confidence: ").append(confidencePercent).append("%. ");
        
        if (confidence < 0.7) {
            explanation.append("Keep logging your periods to improve accuracy!");
        }
        
        return explanation.toString();
    }
}

