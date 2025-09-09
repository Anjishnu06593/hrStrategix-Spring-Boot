package com.example.hrStrategix.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request containing user credentials")
public class LoginRequest {
    @Schema(description = "Username, email, or employee code", example = "rajesh_admin", required = true)
    private String identifier;
    
    @Schema(description = "User password", example = "hashed_pw", required = true)
    private String password;
}
