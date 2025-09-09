package com.example.hrStrategix.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request to create a new user account")
public class CreateUserRequest {
    @Schema(description = "Unique username", example = "test_user", required = true)
    private String username;
    
    @Schema(description = "User email address", example = "test.user@company.com")
    private String email;
    
    @Schema(description = "User password", example = "password123", required = true)
    private String password;
    
    @Schema(description = "Full name of the user", example = "Test User")
    private String fullName;
    
    @Schema(description = "User role", example = "employee", allowableValues = {"employee", "line_manager", "bu_lead", "country_manager", "hr_admin", "admin"}, required = true)
    private String role;
    
    @Schema(description = "Employee ID to link this user to (optional)", example = "2")
    private String employeeId;
}
