package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
public class PerformanceImprovementPlanResponse {
    private Long id;
    private String employeeName;
    private String employeeCode;
    private String planOwnerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String objectives;
    private String actions;
    private String status;
    private String notes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}