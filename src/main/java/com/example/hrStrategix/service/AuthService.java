package com.example.hrStrategix.service;

import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User authenticate(String identifier, String rawPassword) {
        try {
            User user = userService.findByIdentifier(identifier)
                    .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));

            if (Boolean.FALSE.equals(user.getIsActive())) {
                throw new IllegalArgumentException("user is inactive");
            }
            if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
                throw new IllegalArgumentException("invalid credentials");
            }
            return user;
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            throw new IllegalArgumentException("invalid credentials");
        }
    }

    public String issueToken(User user) {
        try {
            return jwtUtil.generateToken(user);
        } catch (Exception e) {
            System.err.println("Token generation error: " + e.getMessage());
            throw new RuntimeException("Failed to generate token");
        }
    }

    public long getJwtExpirationMs() {
        return jwtUtil.getExpirationMs();
    }
}
