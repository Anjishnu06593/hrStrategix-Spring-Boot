package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
public class EmployeeResponse {
    private Long id;
    private String empCode;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private LocalDate dateOfJoining;
    private String employmentType;
    private String locationCity;
    private String businessUnitName;
    private String levelName;
    private String subLevel;
    private String designationTitle;
    private String jobFamilyName;
    private String managerName;
    private String unitLeadName;
    private String genderIdentity;
    private BigDecimal priorExperienceYears;
    private BigDecimal msgGlobalIndiaExp;
    private BigDecimal totalRelevantExp;
    private String email;
    private String phone;
    private Boolean permanentWfh;
    private String workLocationDescription;
    private String status;
    private LocalDate dateOfResignation;
    private LocalDate relievedAsOn;
    private String reasonOfSeparation;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}