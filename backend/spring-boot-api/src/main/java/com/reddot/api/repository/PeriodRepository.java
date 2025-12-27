package com.reddot.api.repository;

import com.reddot.api.model.Period;
import com.reddot.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    List<Period> findByUserOrderByStartDateDesc(User user);
    
    @Query("SELECT p FROM Period p WHERE p.user = :user AND p.startDate BETWEEN :startDate AND :endDate ORDER BY p.startDate DESC")
    List<Period> findByUserAndDateRange(@Param("user") User user, 
                                        @Param("startDate") LocalDate startDate, 
                                        @Param("endDate") LocalDate endDate);
    
    Optional<Period> findFirstByUserOrderByStartDateDesc(User user);
    
    @Query("SELECT p FROM Period p WHERE p.user = :user AND p.startDate <= :date AND (p.endDate IS NULL OR p.endDate >= :date)")
    Optional<Period> findActivePeriodOnDate(@Param("user") User user, @Param("date") LocalDate date);
}

