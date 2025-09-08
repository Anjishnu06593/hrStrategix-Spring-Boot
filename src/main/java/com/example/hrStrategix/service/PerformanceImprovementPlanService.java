package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CreatePerformanceImprovementPlanRequest;
import com.example.hrStrategix.dto.PerformanceImprovementPlanResponse;
import com.example.hrStrategix.entity.Employee;
import com.example.hrStrategix.entity.PerformanceImprovementPlan;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.repository.EmployeeRepository;
import com.example.hrStrategix.repository.PerformanceImprovementPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceImprovementPlanService {
    private final PerformanceImprovementPlanRepository pipRepository;
    private final EmployeeRepository employeeRepository;

    public List<PerformanceImprovementPlanResponse> getAllPIPs() {
        return pipRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PerformanceImprovementPlanResponse getPIPById(Long id) {
        PerformanceImprovementPlan pip = pipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PIP not found with id: " + id));
        return mapToResponse(pip);
    }

    public List<PerformanceImprovementPlanResponse> getPIPsByEmployee(Long employeeId) {
        return pipRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PerformanceImprovementPlanResponse> getPIPsByStatus(String status) {
        return pipRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PerformanceImprovementPlanResponse createPIP(CreatePerformanceImprovementPlanRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Get current user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PerformanceImprovementPlan pip = PerformanceImprovementPlan.builder()
                .employee(employee)
                .planOwner(currentUser)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .objectives(request.getObjectives())
                .actions(request.getActions())
                .status(request.getStatus())
                .notes(request.getNotes())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        PerformanceImprovementPlan savedPIP = pipRepository.save(pip);
        return mapToResponse(savedPIP);
    }

    @Transactional
    public void deletePIP(Long id) {
        if (!pipRepository.existsById(id)) {
            throw new IllegalArgumentException("PIP not found with id: " + id);
        }
        pipRepository.deleteById(id);
    }

    private PerformanceImprovementPlanResponse mapToResponse(PerformanceImprovementPlan pip) {
        return PerformanceImprovementPlanResponse.builder()
                .id(pip.getId())
                .employeeName(pip.getEmployee().getFirstName() + " " + pip.getEmployee().getLastName())
                .employeeCode(pip.getEmployee().getEmpCode())
                .planOwnerName(pip.getPlanOwner() != null ? pip.getPlanOwner().getFullName() : null)
                .startDate(pip.getStartDate())
                .endDate(pip.getEndDate())
                .objectives(pip.getObjectives())
                .actions(pip.getActions())
                .status(pip.getStatus())
                .notes(pip.getNotes())
                .createdAt(pip.getCreatedAt())
                .updatedAt(pip.getUpdatedAt())
                .build();
    }
}