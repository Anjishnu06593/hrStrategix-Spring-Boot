package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreateUserRequest;
import com.example.hrStrategix.dto.UpdateUserRequest;
import com.example.hrStrategix.dto.UserResponse;
import com.example.hrStrategix.entity.User;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Administrative user management APIs for HR and Admin roles")
public class AdminUserController {
    private final UserService userService;

    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER')")
    @PostMapping
    @Operation(
        summary = "Create New User",
        description = "Create a new user account with optional employee linking. Only HR_ADMIN, ADMIN, and COUNTRY_MANAGER can create users."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User created successfully",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Created User",
                    value = """
                    {
                        "id": 13,
                        "username": "test_user",
                        "email": "test.user@company.com",
                        "fullName": "Test User",
                        "role": "EMPLOYEE",
                        "employeeId": 2
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - username/email already exists or invalid employee ID",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Username Exists",
                    value = """
                    {
                        "timestamp": "2024-01-01T10:00:00Z",
                        "status": 400,
                        "error": "Bad Request",
                        "message": "username already exists"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient privileges")
    })
    public ResponseEntity<?> createUser(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User creation details",
            required = true,
            content = @Content(
                schema = @Schema(implementation = CreateUserRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Create Employee User",
                        value = """
                        {
                            "username": "test_user",
                            "email": "test.user@company.com",
                            "password": "password123",
                            "fullName": "Test User",
                            "role": "employee",
                            "employeeId": "2"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Create HR Admin",
                        value = """
                        {
                            "username": "hr_admin_new",
                            "email": "hradmin@company.com",
                            "password": "securepass123",
                            "fullName": "HR Administrator",
                            "role": "hr_admin"
                        }
                        """
                    )
                }
            )
        )
        @RequestBody CreateUserRequest req) {
        User created = userService.createUser(req);
        return ResponseEntity.ok(
                new Object() {
                    public Long id = created.getId();
                    public String username = created.getUsername();
                    public String email = created.getEmail();
                    public String fullName = created.getFullName();
                    public String role = created.getRole().name();
                    public Long employeeId = created.getEmployee() == null ? null : created.getEmployee().getId();
                }
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    @Operation(
        summary = "Get All Users",
        description = "Retrieve paginated list of users with optional filtering by role and username. Only HR_ADMIN and ADMIN can access this endpoint."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "User List",
                    value = """
                    {
                        "content": [
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
                        ],
                        "pageable": {
                            "pageNumber": 0,
                            "pageSize": 10
                        },
                        "totalElements": 12,
                        "totalPages": 2
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient privileges")
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(description = "Filter by user role", example = "employee")
            @RequestParam(required = false) String role,
            @Parameter(description = "Filter by username (partial match)", example = "anita")
            @RequestParam(required = false) String username,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Page<UserResponse> users = userService.getAllUsers(role, username, page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    @Operation(
        summary = "Get User by ID",
        description = "Retrieve specific user details by ID. Admins can view any user, regular users can only view their own profile."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User details retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = @ExampleObject(
                    name = "User Details",
                    value = """
                    {
                        "id": 2,
                        "username": "anita_emp",
                        "email": "anita.sharma@company.com",
                        "fullName": "Anita Sharma",
                        "role": "EMPLOYEE",
                        "employeeId": 2,
                        "employeeCode": "E1002",
                        "employeeName": "Anita Sharma",
                        "isActive": true,
                        "lastLoginAt": "2025-09-01T00:00:00Z",
                        "createdAt": "2025-09-01T00:00:00Z"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "User not found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "User Not Found",
                    value = """
                    {
                        "timestamp": "2024-01-01T10:00:00Z",
                        "status": 400,
                        "error": "Bad Request",
                        "message": "User not found with id: 999"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "403", description = "Forbidden - Access denied")
    })
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "User ID", example = "2", required = true)
            @PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        // Allow users to view their own profile with limited information
        if (!hasAdminRole(currentUser) && !currentUser.getId().equals(id)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    @Operation(
        summary = "Update User",
        description = "Update user information including role, active status, and employee linking. Only HR_ADMIN and ADMIN can update users."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = @ExampleObject(
                    name = "Updated User",
                    value = """
                    {
                        "id": 2,
                        "username": "anita_emp",
                        "email": "anita.sharma.updated@company.com",
                        "fullName": "Anita Sharma Updated",
                        "role": "LINE_MANAGER",
                        "employeeId": 2,
                        "employeeCode": "E1002",
                        "employeeName": "Anita Sharma",
                        "isActive": true,
                        "lastLoginAt": "2025-09-01T00:00:00Z",
                        "createdAt": "2025-09-01T00:00:00Z"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - email already exists or invalid data",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Email Exists",
                    value = """
                    {
                        "timestamp": "2024-01-01T10:00:00Z",
                        "status": 400,
                        "error": "Bad Request",
                        "message": "Email already exists"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient privileges")
    })
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "User ID to update", example = "2", required = true)
            @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User update details (all fields optional)",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = UpdateUserRequest.class),
                    examples = {
                        @ExampleObject(
                            name = "Update Role and Status",
                            value = """
                            {
                                "fullName": "Anita Sharma Updated",
                                "role": "line_manager",
                                "isActive": true
                            }
                            """
                        ),
                        @ExampleObject(
                            name = "Update Email and Employee Link",
                            value = """
                            {
                                "email": "anita.new@company.com",
                                "employeeId": 5
                            }
                            """
                        )
                    }
                )
            )
            @RequestBody UpdateUserRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Deactivate User",
        description = "Soft delete user by setting isActive=false. Only ADMIN role can deactivate users. This preserves data integrity while preventing login."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "User deactivated successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "User not found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "User Not Found",
                    value = """
                    {
                        "timestamp": "2024-01-01T10:00:00Z",
                        "status": 400,
                        "error": "Bad Request",
                        "message": "User not found with id: 999"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "403", description = "Forbidden - Only ADMIN can deactivate users")
    })
    public ResponseEntity<Void> deactivateUser(
            @Parameter(description = "User ID to deactivate", example = "2", required = true)
            @PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    private boolean hasAdminRole(User user) {
        return user.getRole().name().equals("HR_ADMIN") || user.getRole().name().equals("ADMIN");
    }
}