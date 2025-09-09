package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private Boolean isActive;
    private OffsetDateTime lastLoginAt;
    private OffsetDateTime createdAt;
}