package com.example.hrStrategix.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private UserInfo user;
    @Data
    public static class UserInfo {
        private Long id;
        private String username;
        private String fullName;
        private String email;
        private String role;
        private Long employeeId;      // link to employee.id
        private String employeeCode;  // employee.empCode if present
    }
}
