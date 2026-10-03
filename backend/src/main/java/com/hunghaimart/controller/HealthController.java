package com.hunghaimart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok("Application is running and database connection is healthy");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Application is running but database connection failed: " + e.getMessage());
        }
    }
}
