package com.reddot.api.service;

import com.reddot.api.model.Notification;
import com.reddot.api.model.User;
import com.reddot.api.repository.NotificationRepository;
import com.reddot.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<Notification> getUserNotifications() {
        User user = getCurrentUser();
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Notification> getUnreadNotifications() {
        User user = getCurrentUser();
        return notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user);
    }
    
    @Transactional
    public Notification markAsRead(Long notificationId) {
        User user = getCurrentUser();
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        
        notification.setRead(true);
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public void markAllAsRead() {
        User user = getCurrentUser();
        List<Notification> notifications = notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }
    
    @Transactional
    public Notification createNotification(Notification notification) {
        User user = getCurrentUser();
        notification.setUser(user);
        if (notification.getScheduledTime() == null) {
            notification.setScheduledTime(LocalDateTime.now());
        }
        return notificationRepository.save(notification);
    }
    
    // Scheduled task to send pending notifications
    @Scheduled(fixedRate = 60000) // Every minute
    public void sendPendingNotifications() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<Notification> pending = notificationRepository.findPendingNotifications(user, LocalDateTime.now());
            // In production, this would trigger email/push notifications
            // For now, notifications are already in the database
        }
    }
}

