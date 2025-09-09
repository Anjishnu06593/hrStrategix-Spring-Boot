package com.example.hrStrategix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Health Check", description = "Application health monitoring")
public class HealthCheck {
    @GetMapping("/health")
    @Operation(
        summary = "Health Check",
        description = "Check if the application is running and healthy. No authentication required."
    )
    @ApiResponse(responseCode = "200", description = "Application is healthy")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }
}
