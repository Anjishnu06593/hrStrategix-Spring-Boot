package com.example.hrStrategix.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateEmployeeRequest {
    private String empCode;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private LocalDate dateOfJoining;
    private String employmentType;
    private Long locationId;
    private Long businessUnitId;
    private Long levelId;
    private String subLevel;
    private Long designationId;
    private Long jobFamilyId;
    private Long managerId;
    private Long unitLeadId;
    private String genderIdentity;
    private BigDecimal priorExperienceYears;
    private BigDecimal msgGlobalIndiaExp;
    private String email;
    private String phone;
    private Boolean permanentWfh;
    private String workLocationDescription;
    private String status;
}