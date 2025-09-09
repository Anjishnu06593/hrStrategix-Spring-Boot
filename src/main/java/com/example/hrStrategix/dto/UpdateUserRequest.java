package com.example.hrStrategix.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    private String fullName;
    private String role;
    private Long employeeId;
    private Boolean isActive;
}