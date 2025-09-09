package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CreateUserRequest;
import com.example.hrStrategix.dto.UpdateUserRequest;
import com.example.hrStrategix.dto.UserResponse;
import com.example.hrStrategix.entity.Employee;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.entity.UserRole;
import com.example.hrStrategix.repository.EmployeeRepository;
import com.example.hrStrategix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public UserResponse getCurrentUserProfile(User currentUser) {
        return mapToResponse(currentUser);
    }

    public Page<UserResponse> getAllUsers(String role, String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        Page<User> users;
        if (role != null && username != null) {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            users = userRepository.findByRoleAndUsernameContainingIgnoreCase(userRole, username, pageable);
        } else if (role != null) {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            users = userRepository.findByRole(userRole, pageable);
        } else if (username != null) {
            users = userRepository.findByUsernameContainingIgnoreCase(username, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        
        return users.map(this::mapToResponse);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Update fields
        if (request.getEmail() != null) {
            // Check if email is already taken by another user
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }
        
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        
        if (request.getRole() != null) {
            user.setRole(UserRole.valueOf(request.getRole().toUpperCase()));
        }
        
        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
            user.setEmployee(employee);
        }
        
        if (request.getIsActive() != null) {
            user.setIsActive(request.getIsActive());
        }

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        
        user.setIsActive(false);
        userRepository.save(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .employeeId(user.getEmployee() != null ? user.getEmployee().getId() : null)
                .employeeCode(user.getEmployee() != null ? user.getEmployee().getEmpCode() : null)
                .employeeName(user.getEmployee() != null ? 
                    user.getEmployee().getFirstName() + " " + user.getEmployee().getLastName() : null)
                .isActive(user.getIsActive())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
