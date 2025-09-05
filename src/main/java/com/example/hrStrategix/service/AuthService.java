package com.example.hrStrategix.service;

import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtUtil jwtUtil;

    public User authenticate(String identifier, String rawPassword) {
        User user = userService.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new IllegalArgumentException("user is inactive");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid credentials");
        }
        return user;
    }

    public String issueToken(User user) {
        return jwtUtil.generateToken(user);
    }

    public long getJwtExpirationMs() {
        return jwtUtil.getExpirationMs();
    }

}
