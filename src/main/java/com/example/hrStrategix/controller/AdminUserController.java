package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreateUserRequest;
import com.example.hrStrategix.dto.UpdateUserRequest;
import com.example.hrStrategix.dto.UserResponse;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER')")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest req) {
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
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserResponse> users = userService.getAllUsers(role, username, page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        // Allow users to view their own profile with limited information
        if (!hasAdminRole(currentUser) && !currentUser.getId().equals(id)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    private boolean hasAdminRole(User user) {
        return user.getRole().name().equals("HR_ADMIN") || user.getRole().name().equals("ADMIN");
    }
}