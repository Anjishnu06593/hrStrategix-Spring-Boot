package com.example.hrStrategix.controller;

import com.example.hrStrategix.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final EmployeeRepository employeeRepository;
    private final CandidateRepository candidateRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final PerformanceImprovementPlanRepository pipRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Employee statistics
        long totalEmployees = employeeRepository.count();
        stats.put("totalEmployees", totalEmployees);
        
        // Candidate statistics
        long totalCandidates = candidateRepository.count();
        long activeCandidates = candidateRepository.findByStatus("applied").size() + 
                               candidateRepository.findByStatus("screened").size() + 
                               candidateRepository.findByStatus("interviewed").size();
        long hiredCandidates = candidateRepository.findByStatus("hired").size();
        
        stats.put("totalCandidates", totalCandidates);
        stats.put("activeCandidates", activeCandidates);
        stats.put("hiredCandidates", hiredCandidates);
        
        // Performance statistics
        long totalReviews = performanceReviewRepository.count();
        long activePIPs = pipRepository.findByStatus("open").size();
        
        stats.put("totalPerformanceReviews", totalReviews);
        stats.put("activePIPs", activePIPs);
        
        return ResponseEntity.ok(stats);
    }
}