package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.LoginRequest;
import com.example.hrStrategix.dto.LoginResponse;
import com.example.hrStrategix.dto.UserResponse;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.service.AuthService;
import com.example.hrStrategix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
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
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        UserResponse userProfile = userService.getCurrentUserProfile(currentUser);
        return ResponseEntity.ok(userProfile);
    }
}
