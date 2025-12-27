package com.reddot.api.repository;

import com.reddot.api.model.Symptom;
import com.reddot.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByUserOrderByDateDesc(User user);
    
    @Query("SELECT s FROM Symptom s WHERE s.user = :user AND s.date BETWEEN :startDate AND :endDate ORDER BY s.date DESC")
    List<Symptom> findByUserAndDateRange(@Param("user") User user, 
                                        @Param("startDate") LocalDate startDate, 
                                        @Param("endDate") LocalDate endDate);
}

