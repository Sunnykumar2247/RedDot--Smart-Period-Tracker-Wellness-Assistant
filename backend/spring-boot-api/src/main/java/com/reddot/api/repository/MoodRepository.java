package com.reddot.api.repository;

import com.reddot.api.model.Mood;
import com.reddot.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {
    List<Mood> findByUserOrderByDateDesc(User user);
    
    @Query("SELECT m FROM Mood m WHERE m.user = :user AND m.date BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
    List<Mood> findByUserAndDateRange(@Param("user") User user, 
                                     @Param("startDate") LocalDate startDate, 
                                     @Param("endDate") LocalDate endDate);
}

