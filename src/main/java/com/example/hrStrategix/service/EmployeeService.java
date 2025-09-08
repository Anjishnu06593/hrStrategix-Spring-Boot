package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CreateEmployeeRequest;
import com.example.hrStrategix.dto.EmployeeResponse;
import com.example.hrStrategix.dto.UpdateEmployeeRequest;
import com.example.hrStrategix.entity.*;
import com.example.hrStrategix.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final LocationRepository locationRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final LevelRepository levelRepository;
    private final DesignationRepository designationRepository;
    private final JobFamilyRepository jobFamilyRepository;

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        return mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByCode(String empCode) {
        Employee employee = employeeRepository.findByEmpCode(empCode)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with code: " + empCode));
        return mapToResponse(employee);
    }

    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.findByEmpCode(request.getEmpCode()).isPresent()) {
            throw new IllegalArgumentException("Employee code already exists: " + request.getEmpCode());
        }

        Employee employee = Employee.builder()
                .empCode(request.getEmpCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .dob(request.getDob())
                .dateOfJoining(request.getDateOfJoining())
                .employmentType(request.getEmploymentType())
                .genderIdentity(request.getGenderIdentity())
                .priorExperienceYears(request.getPriorExperienceYears())
                .msgGlobalIndiaExp(request.getMsgGlobalIndiaExp())
                .email(request.getEmail())
                .phone(request.getPhone())
                .permanentWfh(request.getPermanentWfh())
                .workLocationDescription(request.getWorkLocationDescription())
                .status(request.getStatus())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        // Set relationships
        if (request.getLocationId() != null) {
            Location location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Location not found"));
            employee.setLocation(location);
        }

        if (request.getBusinessUnitId() != null) {
            BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
                    .orElseThrow(() -> new IllegalArgumentException("Business unit not found"));
            employee.setBusinessUnit(businessUnit);
        }

        if (request.getLevelId() != null) {
            Level level = levelRepository.findById(request.getLevelId())
                    .orElseThrow(() -> new IllegalArgumentException("Level not found"));
            employee.setLevel(level);
            employee.setSubLevel(request.getSubLevel());
        }

        if (request.getDesignationId() != null) {
            Designation designation = designationRepository.findById(request.getDesignationId())
                    .orElseThrow(() -> new IllegalArgumentException("Designation not found"));
            employee.setDesignation(designation);
        }

        if (request.getJobFamilyId() != null) {
            JobFamily jobFamily = jobFamilyRepository.findById(request.getJobFamilyId())
                    .orElseThrow(() -> new IllegalArgumentException("Job family not found"));
            employee.setJobFamily(jobFamily);
        }

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            employee.setManager(manager);
        }

        if (request.getUnitLeadId() != null) {
            Employee unitLead = employeeRepository.findById(request.getUnitLeadId())
                    .orElseThrow(() -> new IllegalArgumentException("Unit lead not found"));
            employee.setUnitLead(unitLead);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        // Update fields
        if (request.getFirstName() != null) employee.setFirstName(request.getFirstName());
        if (request.getLastName() != null) employee.setLastName(request.getLastName());
        if (request.getGender() != null) employee.setGender(request.getGender());
        if (request.getDob() != null) employee.setDob(request.getDob());
        if (request.getEmploymentType() != null) employee.setEmploymentType(request.getEmploymentType());
        if (request.getGenderIdentity() != null) employee.setGenderIdentity(request.getGenderIdentity());
        if (request.getPriorExperienceYears() != null) employee.setPriorExperienceYears(request.getPriorExperienceYears());
        if (request.getMsgGlobalIndiaExp() != null) employee.setMsgGlobalIndiaExp(request.getMsgGlobalIndiaExp());
        if (request.getEmail() != null) employee.setEmail(request.getEmail());
        if (request.getPhone() != null) employee.setPhone(request.getPhone());
        if (request.getPermanentWfh() != null) employee.setPermanentWfh(request.getPermanentWfh());
        if (request.getWorkLocationDescription() != null) employee.setWorkLocationDescription(request.getWorkLocationDescription());
        if (request.getStatus() != null) employee.setStatus(request.getStatus());
        if (request.getDateOfResignation() != null) employee.setDateOfResignation(request.getDateOfResignation());
        if (request.getRelievedAsOn() != null) employee.setRelievedAsOn(request.getRelievedAsOn());
        if (request.getReasonOfSeparation() != null) employee.setReasonOfSeparation(request.getReasonOfSeparation());

        employee.setUpdatedAt(OffsetDateTime.now());

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .empCode(employee.getEmpCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .gender(employee.getGender())
                .dob(employee.getDob())
                .dateOfJoining(employee.getDateOfJoining())
                .employmentType(employee.getEmploymentType())
                .locationCity(employee.getLocation() != null ? employee.getLocation().getCity() : null)
                .businessUnitName(employee.getBusinessUnit() != null ? employee.getBusinessUnit().getName() : null)
                .levelName(employee.getLevel() != null ? employee.getLevel().getName() : null)
                .subLevel(employee.getSubLevel())
                .designationTitle(employee.getDesignation() != null ? employee.getDesignation().getTitle() : null)
                .jobFamilyName(employee.getJobFamily() != null ? employee.getJobFamily().getName() : null)
                .managerName(employee.getManager() != null ? 
                    employee.getManager().getFirstName() + " " + employee.getManager().getLastName() : null)
                .unitLeadName(employee.getUnitLead() != null ? 
                    employee.getUnitLead().getFirstName() + " " + employee.getUnitLead().getLastName() : null)
                .genderIdentity(employee.getGenderIdentity())
                .priorExperienceYears(employee.getPriorExperienceYears())
                .msgGlobalIndiaExp(employee.getMsgGlobalIndiaExp())
                .totalRelevantExp(employee.getTotalRelevantExp())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .permanentWfh(employee.getPermanentWfh())
                .workLocationDescription(employee.getWorkLocationDescription())
                .status(employee.getStatus())
                .dateOfResignation(employee.getDateOfResignation())
                .relievedAsOn(employee.getRelievedAsOn())
                .reasonOfSeparation(employee.getReasonOfSeparation())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}