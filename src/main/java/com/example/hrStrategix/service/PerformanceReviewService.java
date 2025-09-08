package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CreatePerformanceReviewRequest;
import com.example.hrStrategix.dto.PerformanceReviewResponse;
import com.example.hrStrategix.entity.Employee;
import com.example.hrStrategix.entity.PerformanceReview;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.repository.EmployeeRepository;
import com.example.hrStrategix.repository.PerformanceReviewRepository;
import com.example.hrStrategix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceReviewService {
    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public List<PerformanceReviewResponse> getAllPerformanceReviews() {
        return performanceReviewRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PerformanceReviewResponse getPerformanceReviewById(Long id) {
        PerformanceReview review = performanceReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance review not found with id: " + id));
        return mapToResponse(review);
    }

    public List<PerformanceReviewResponse> getPerformanceReviewsByEmployee(Long employeeId) {
        return performanceReviewRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PerformanceReviewResponse> getPerformanceReviewsByDateRange(LocalDate startDate, LocalDate endDate) {
        return performanceReviewRepository.findByReviewDateBetween(startDate, endDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PerformanceReviewResponse createPerformanceReview(CreatePerformanceReviewRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        Employee reviewer = null;
        if (request.getReviewerId() != null) {
            reviewer = employeeRepository.findById(request.getReviewerId())
                    .orElseThrow(() -> new IllegalArgumentException("Reviewer not found"));
        }

        // Get current user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PerformanceReview review = PerformanceReview.builder()
                .employee(employee)
                .reviewer(reviewer)
                .reviewPeriodStart(request.getReviewPeriodStart())
                .reviewPeriodEnd(request.getReviewPeriodEnd())
                .reviewDate(request.getReviewDate())
                .score(request.getScore())
                .ratingScale(request.getRatingScale())
                .comments(request.getComments())
                .createdBy(currentUser)
                .createdAt(OffsetDateTime.now())
                .build();

        PerformanceReview savedReview = performanceReviewRepository.save(review);
        return mapToResponse(savedReview);
    }

    @Transactional
    public void deletePerformanceReview(Long id) {
        if (!performanceReviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Performance review not found with id: " + id);
        }
        performanceReviewRepository.deleteById(id);
    }

    private PerformanceReviewResponse mapToResponse(PerformanceReview review) {
        return PerformanceReviewResponse.builder()
                .id(review.getId())
                .employeeName(review.getEmployee().getFirstName() + " " + review.getEmployee().getLastName())
                .employeeCode(review.getEmployee().getEmpCode())
                .reviewerName(review.getReviewer() != null ? 
                    review.getReviewer().getFirstName() + " " + review.getReviewer().getLastName() : null)
                .reviewPeriodStart(review.getReviewPeriodStart())
                .reviewPeriodEnd(review.getReviewPeriodEnd())
                .reviewDate(review.getReviewDate())
                .score(review.getScore())
                .ratingScale(review.getRatingScale())
                .comments(review.getComments())
                .createdByName(review.getCreatedBy() != null ? review.getCreatedBy().getFullName() : null)
                .createdAt(review.getCreatedAt())
                .build();
    }
}