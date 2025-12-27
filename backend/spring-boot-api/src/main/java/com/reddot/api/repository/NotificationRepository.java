package com.reddot.api.repository;

import com.reddot.api.model.Notification;
import com.reddot.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    List<Notification> findByUserAndReadFalseOrderByCreatedAtDesc(User user);
    
    @Query("SELECT n FROM Notification n WHERE n.user = :user AND n.scheduledTime <= :now AND n.read = false ORDER BY n.scheduledTime ASC")
    List<Notification> findPendingNotifications(@Param("user") User user, @Param("now") LocalDateTime now);
}

