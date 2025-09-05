package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreateUserRequest;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
