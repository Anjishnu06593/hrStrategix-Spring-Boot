package com.example.hrStrategix.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Login response containing JWT token and user information")
public class LoginResponse {
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    
    @Schema(description = "Token type", example = "bearer")
    private String tokenType;
    
    @Schema(description = "Token expiration time in milliseconds", example = "3600000")
    private Long expiresIn;
    
    @Schema(description = "User information")
    private UserInfo user;
    
    @Data
    @Schema(description = "User information included in login response")
    public static class UserInfo {
        @Schema(description = "User ID", example = "1")
        private Long id;
        
        @Schema(description = "Username", example = "rajesh_admin")
        private String username;
        
        @Schema(description = "Full name", example = "Rajesh Kumar")
        private String fullName;
        
        @Schema(description = "Email address", example = "rajesh.kumar@company.com")
        private String email;
        
        @Schema(description = "User role", example = "ADMIN")
        private String role;
        
        @Schema(description = "Linked employee ID", example = "1")
        private Long employeeId;      // link to employee.id
        
        @Schema(description = "Employee code", example = "E1001")
        private String employeeCode;  // employee.empCode if present
    }
}
