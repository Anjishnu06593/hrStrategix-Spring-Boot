package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreatePerformanceImprovementPlanRequest;
import com.example.hrStrategix.dto.PerformanceImprovementPlanResponse;
import com.example.hrStrategix.service.PerformanceImprovementPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/performance-improvement-plans")
@RequiredArgsConstructor
public class PerformanceImprovementPlanController {
    private final PerformanceImprovementPlanService pipService;

    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<PerformanceImprovementPlanResponse>> getAllPIPs() {
        List<PerformanceImprovementPlanResponse> pips = pipService.getAllPIPs();
        return ResponseEntity.ok(pips);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<PerformanceImprovementPlanResponse> getPIPById(@PathVariable Long id) {
        PerformanceImprovementPlanResponse pip = pipService.getPIPById(id);
        return ResponseEntity.ok(pip);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<PerformanceImprovementPlanResponse>> getPIPsByEmployee(@PathVariable Long employeeId) {
        List<PerformanceImprovementPlanResponse> pips = pipService.getPIPsByEmployee(employeeId);
        return ResponseEntity.ok(pips);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<PerformanceImprovementPlanResponse>> getPIPsByStatus(@PathVariable String status) {
        List<PerformanceImprovementPlanResponse> pips = pipService.getPIPsByStatus(status);
        return ResponseEntity.ok(pips);
    }

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<PerformanceImprovementPlanResponse> createPIP(@RequestBody CreatePerformanceImprovementPlanRequest request) {
        PerformanceImprovementPlanResponse pip = pipService.createPIP(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pip);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePIP(@PathVariable Long id) {
        pipService.deletePIP(id);
        return ResponseEntity.noContent().build();
    }
}