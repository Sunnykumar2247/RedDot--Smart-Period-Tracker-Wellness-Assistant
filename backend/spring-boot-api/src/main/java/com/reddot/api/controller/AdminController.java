package com.reddot.api.controller;

import com.reddot.api.model.User;
import com.reddot.api.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin dashboard endpoints")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/stats")
    @Operation(summary = "Get admin statistics", description = "Get platform statistics")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.findAll().stream()
                .filter(User::getActive)
                .count();
        
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", activeUsers);
        stats.put("inactiveUsers", totalUsers - activeUsers);
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Get list of all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}

