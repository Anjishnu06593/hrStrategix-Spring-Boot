package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreateRecruitmentMetricsRequest;
import com.example.hrStrategix.dto.RecruitmentMetricsResponse;
import com.example.hrStrategix.service.RecruitmentMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recruitment-metrics")
@RequiredArgsConstructor
public class RecruitmentMetricsController {
    private final RecruitmentMetricsService metricsService;

    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<RecruitmentMetricsResponse>> getAllMetrics() {
        List<RecruitmentMetricsResponse> metrics = metricsService.getAllMetrics();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<RecruitmentMetricsResponse> getMetricsById(@PathVariable Long id) {
        RecruitmentMetricsResponse metrics = metricsService.getMetricsById(id);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/channel/{channelId}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<RecruitmentMetricsResponse>> getMetricsByChannel(@PathVariable Long channelId) {
        List<RecruitmentMetricsResponse> metrics = metricsService.getMetricsByChannel(channelId);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<RecruitmentMetricsResponse>> getMetricsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<RecruitmentMetricsResponse> metrics = metricsService.getMetricsByDateRange(startDate, endDate);
        return ResponseEntity.ok(metrics);
    }

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER')")
    public ResponseEntity<RecruitmentMetricsResponse> createMetrics(@RequestBody CreateRecruitmentMetricsRequest request) {
        RecruitmentMetricsResponse metrics = metricsService.createMetrics(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(metrics);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMetrics(@PathVariable Long id) {
        metricsService.deleteMetrics(id);
        return ResponseEntity.noContent().build();
    }
}