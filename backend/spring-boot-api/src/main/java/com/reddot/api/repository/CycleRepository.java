package com.reddot.api.repository;

import com.reddot.api.model.Cycle;
import com.reddot.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {
    List<Cycle> findByUserOrderByCycleStartDateDesc(User user);
    
    Optional<Cycle> findFirstByUserOrderByCycleStartDateDesc(User user);
    
    @Query("SELECT c FROM Cycle c WHERE c.user = :user AND c.cycleStartDate BETWEEN :startDate AND :endDate ORDER BY c.cycleStartDate DESC")
    List<Cycle> findByUserAndDateRange(@Param("user") User user, 
                                       @Param("startDate") LocalDate startDate, 
                                       @Param("endDate") LocalDate endDate);
}

