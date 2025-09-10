package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.LoginRequest;
import com.example.hrStrategix.dto.LoginResponse;
import com.example.hrStrategix.dto.UserResponse;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.service.AuthService;
import com.example.hrStrategix.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and user session management APIs")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(
        summary = "User Login",
        description = "Authenticate user with username/email/employee_code and password. Returns JWT token for subsequent API calls."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class),
                examples = @ExampleObject(
                    name = "Successful Login",
                    value = """
                    {
                        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                        "tokenType": "bearer",
                        "expiresIn": 3600000,
                        "user": {
                            "id": 1,
                            "username": "rajesh_admin",
                            "fullName": "Rajesh Kumar",
                            "email": "rajesh.kumar@company.com",
                            "role": "ADMIN",
                            "employeeId": 1,
                            "employeeCode": "E1001"
                        }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid credentials or inactive user",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Invalid Credentials",
                    value = """
                    {
                        "timestamp": "2024-01-01T10:00:00Z",
                        "status": 400,
                        "error": "Bad Request",
                        "message": "invalid credentials"
                    }
                    """
                )
            )
        )
    })
    public ResponseEntity<?> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login credentials - can use username, email, or employee code as identifier",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Admin Login",
                        value = """
                        {
                            "identifier": "rajesh_admin",
                            "password": "hashed_pw"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Email Login",
                        value = """
                        {
                            "identifier": "meena.iyer@company.com",
                            "password": "hashed_pw"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Employee Code Login",
                        value = """
                        {
                            "identifier": "E1001",
                            "password": "hashed_pw"
                        }
                        """
                    )
                }
            )
        )
        @RequestBody LoginRequest req) {
        try {
            User user = authService.authenticate(req.getIdentifier(), req.getPassword());
            String token = authService.issueToken(user);

            LoginResponse.UserInfo info = new LoginResponse.UserInfo();
            info.setId(user.getId());
            info.setUsername(user.getUsername());
            info.setFullName(user.getFullName());
            info.setEmail(user.getEmail());
            info.setRole(user.getRole().name());
            if (user.getEmployee() != null) {
                info.setEmployeeId(user.getEmployee().getId());
                info.setEmployeeCode(user.getEmployee().getEmpCode());
            }

            LoginResponse resp = LoginResponse.builder()
                    .accessToken(token)
                    .tokenType("bearer")
                    .expiresIn(authService.getJwtExpirationMs())
                    .user(info)
                    .build();

            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            throw e; // Let the global exception handler deal with it
        }
    }

    @GetMapping("/me")
    @Operation(
        summary = "Get Current User Profile",
        description = "Retrieve the profile information of the currently authenticated user including linked employee details"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User profile retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = @ExampleObject(
                    name = "User Profile",
                    value = """
                    {
                        "id": 1,
                        "username": "rajesh_admin",
                        "email": "rajesh.kumar@company.com",
                        "fullName": "Rajesh Kumar",
                        "role": "ADMIN",
                        "employeeId": 1,
                        "employeeCode": "E1001",
                        "employeeName": "Rajesh Kumar",
                        "isActive": true,
                        "lastLoginAt": "2025-09-01T00:00:00Z",
                        "createdAt": "2025-09-01T00:00:00Z"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Invalid or missing JWT token"
        )
    })
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        UserResponse userProfile = userService.getCurrentUserProfile(currentUser);
        return ResponseEntity.ok(userProfile);
    }
}
