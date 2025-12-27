package com.reddot.api.controller;

import com.reddot.api.model.Notification;
import com.reddot.api.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Notification endpoints")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping
    @Operation(summary = "Get all notifications", description = "Get all notifications for current user")
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = notificationService.getUserNotifications();
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/unread")
    @Operation(summary = "Get unread notifications", description = "Get unread notifications")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        List<Notification> notifications = notificationService.getUnreadNotifications();
        return ResponseEntity.ok(notifications);
    }
    
    @PutMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a notification as read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(notification);
    }
    
    @PutMapping("/read-all")
    @Operation(summary = "Mark all as read", description = "Mark all notifications as read")
    public ResponseEntity<Void> markAllAsRead() {
        notificationService.markAllAsRead();
        return ResponseEntity.ok().build();
    }
}

