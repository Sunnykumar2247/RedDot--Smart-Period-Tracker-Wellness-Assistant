package com.reddot.api.repository;

import com.reddot.api.model.WellnessLog;
import com.reddot.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WellnessLogRepository extends JpaRepository<WellnessLog, Long> {
    List<WellnessLog> findByUserOrderByDateDesc(User user);
    
    @Query("SELECT w FROM WellnessLog w WHERE w.user = :user AND w.date BETWEEN :startDate AND :endDate ORDER BY w.date DESC")
    List<WellnessLog> findByUserAndDateRange(@Param("user") User user, 
                                             @Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate);
    
    Optional<WellnessLog> findByUserAndDate(User user, LocalDate date);
}

