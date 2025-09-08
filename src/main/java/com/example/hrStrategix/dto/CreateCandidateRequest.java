package com.example.hrStrategix.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateCandidateRequest {
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private LocalDate appliedDate;
    private String appliedPosition;
    private Long sourceId;
    private Long recruiterUserId;
    private String status;
    private BigDecimal experienceYears;
    private BigDecimal candidateExperienceScore;
    private BigDecimal costOfHire;
    private Boolean offerAccepted;
    private Long hireEmployeeId;
    private LocalDate hiredDate;
    private String feedback;
}