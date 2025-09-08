package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
public class CandidateResponse {
    private Long id;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private LocalDate appliedDate;
    private String appliedPosition;
    private String sourceName;
    private String recruiterName;
    private String status;
    private BigDecimal experienceYears;
    private BigDecimal candidateExperienceScore;
    private BigDecimal costOfHire;
    private Boolean offerAccepted;
    private String hireEmployeeName;
    private LocalDate hiredDate;
    private String feedback;
    private OffsetDateTime createdAt;
}