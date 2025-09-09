package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("select u from User u join u.employee e where e.empCode = :empCode")
    Optional<User> findByEmployeeEmpCode(@Param("empCode") String empCode);
    
    Page<User> findByRole(UserRole role, Pageable pageable);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    Page<User> findByRoleAndUsernameContainingIgnoreCase(UserRole role, String username, Pageable pageable);
}
