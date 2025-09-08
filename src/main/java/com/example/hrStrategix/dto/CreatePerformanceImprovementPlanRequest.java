package com.example.hrStrategix.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePerformanceImprovementPlanRequest {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String objectives;
    private String actions;
    private String status;
    private String notes;
}