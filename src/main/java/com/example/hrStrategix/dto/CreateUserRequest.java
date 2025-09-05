package com.example.hrStrategix.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String role;
    private String employeeId;
}
