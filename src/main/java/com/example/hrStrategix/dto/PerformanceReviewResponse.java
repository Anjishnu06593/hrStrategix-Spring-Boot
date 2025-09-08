package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
public class PerformanceReviewResponse {
    private Long id;
    private String employeeName;
    private String employeeCode;
    private String reviewerName;
    private LocalDate reviewPeriodStart;
    private LocalDate reviewPeriodEnd;
    private LocalDate reviewDate;
    private BigDecimal score;
    private String ratingScale;
    private String comments;
    private String createdByName;
    private OffsetDateTime createdAt;
}