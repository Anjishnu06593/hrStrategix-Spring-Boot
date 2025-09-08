package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreatePerformanceReviewRequest;
import com.example.hrStrategix.dto.PerformanceReviewResponse;
import com.example.hrStrategix.service.PerformanceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/performance-reviews")
@RequiredArgsConstructor
public class PerformanceReviewController {
    private final PerformanceReviewService performanceReviewService;

    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<PerformanceReviewResponse>> getAllPerformanceReviews() {
        List<PerformanceReviewResponse> reviews = performanceReviewService.getAllPerformanceReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<PerformanceReviewResponse> getPerformanceReviewById(@PathVariable Long id) {
        PerformanceReviewResponse review = performanceReviewService.getPerformanceReviewById(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<PerformanceReviewResponse>> getPerformanceReviewsByEmployee(@PathVariable Long employeeId) {
        List<PerformanceReviewResponse> reviews = performanceReviewService.getPerformanceReviewsByEmployee(employeeId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<PerformanceReviewResponse>> getPerformanceReviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PerformanceReviewResponse> reviews = performanceReviewService.getPerformanceReviewsByDateRange(startDate, endDate);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<PerformanceReviewResponse> createPerformanceReview(@RequestBody CreatePerformanceReviewRequest request) {
        PerformanceReviewResponse review = performanceReviewService.createPerformanceReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePerformanceReview(@PathVariable Long id) {
        performanceReviewService.deletePerformanceReview(id);
        return ResponseEntity.noContent().build();
    }
}