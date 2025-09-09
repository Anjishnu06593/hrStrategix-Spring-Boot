package com.example.hrStrategix.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@Schema(description = "User information response")
public class UserResponse {
    @Schema(description = "User ID", example = "1")
    private Long id;
    
    @Schema(description = "Username", example = "rajesh_admin")
    private String username;
    
    @Schema(description = "Email address", example = "rajesh.kumar@company.com")
    private String email;
    
    @Schema(description = "Full name", example = "Rajesh Kumar")
    private String fullName;
    
    @Schema(description = "User role", example = "ADMIN")
    private String role;
    
    @Schema(description = "Linked employee ID", example = "1")
    private Long employeeId;
    
    @Schema(description = "Employee code", example = "E1001")
    private String employeeCode;
    
    @Schema(description = "Employee name", example = "Rajesh Kumar")
    private String employeeName;
    
    @Schema(description = "Whether user is active", example = "true")
    private Boolean isActive;
    
    @Schema(description = "Last login timestamp", example = "2025-09-01T00:00:00Z")
    private OffsetDateTime lastLoginAt;
    
    @Schema(description = "Account creation timestamp", example = "2025-09-01T00:00:00Z")
    private OffsetDateTime createdAt;
}