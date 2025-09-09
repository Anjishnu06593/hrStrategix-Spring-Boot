package com.example.hrStrategix.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request to update user information")
public class UpdateUserRequest {
    @Schema(description = "Updated email address", example = "updated.email@company.com")
    private String email;
    
    @Schema(description = "Updated full name", example = "Updated Full Name")
    private String fullName;
    
    @Schema(description = "Updated user role", example = "line_manager", allowableValues = {"employee", "line_manager", "bu_lead", "country_manager", "hr_admin", "admin"})
    private String role;
    
    @Schema(description = "Employee ID to link this user to", example = "5")
    private Long employeeId;
    
    @Schema(description = "Whether the user is active", example = "true")
    private Boolean isActive;
}