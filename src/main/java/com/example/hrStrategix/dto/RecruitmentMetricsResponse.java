package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RecruitmentMetricsResponse {
    private Long id;
    private String channelName;
    private LocalDate metricDate;
    private BigDecimal costSpent;
    private Integer hiresCount;
    private BigDecimal avgTimeToHireDays;
    private String metadata;
}