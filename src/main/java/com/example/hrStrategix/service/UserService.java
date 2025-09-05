package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CreateUserRequest;
import com.example.hrStrategix.entity.Employee;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.entity.UserRole;
import com.example.hrStrategix.repository.EmployeeRepository;
import com.example.hrStrategix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;


    public Optional<User> findByIdentifier(String identifier) {
        Optional<User> u = userRepository.findByUsername(identifier);
        if (u.isPresent()) return u;
        u = userRepository.findByEmail(identifier);
        if (u.isPresent()) return u;
        return userRepository.findByEmployeeEmpCode(identifier);
    }

    @Transactional
    public User createUser(CreateUserRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("username already exists");
        }
        if (req.getEmail() != null && userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("email already exists");
        }
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .fullName(req.getFullName())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(UserRole.valueOf(req.getRole().toUpperCase()))
                .isActive(true)
                .build();

        if (req.getEmployeeId() != null) {
            Employee e = employeeRepository.findById(Long.valueOf(req.getEmployeeId()))
                    .orElseThrow(() -> new IllegalArgumentException("employee_id not found"));
            user.setEmployee(e);
        }
        return userRepository.save(user);
    }
}
