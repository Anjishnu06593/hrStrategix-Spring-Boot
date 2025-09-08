package com.example.hrStrategix.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateRecruitmentMetricsRequest {
    private Long recruitmentChannelId;
    private LocalDate metricDate;
    private BigDecimal costSpent;
    private Integer hiresCount;
    private BigDecimal avgTimeToHireDays;
    private String metadata;
}