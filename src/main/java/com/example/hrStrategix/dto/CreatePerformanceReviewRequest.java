package com.example.hrStrategix.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreatePerformanceReviewRequest {
    private Long employeeId;
    private Long reviewerId;
    private LocalDate reviewPeriodStart;
    private LocalDate reviewPeriodEnd;
    private LocalDate reviewDate;
    private BigDecimal score;
    private String ratingScale;
    private String comments;
}