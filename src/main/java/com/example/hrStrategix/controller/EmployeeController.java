package com.example.hrStrategix.controller;

import com.example.hrStrategix.dto.CreateEmployeeRequest;
import com.example.hrStrategix.dto.EmployeeResponse;
import com.example.hrStrategix.dto.UpdateEmployeeRequest;
import com.example.hrStrategix.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/code/{empCode}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<EmployeeResponse> getEmployeeByCode(@PathVariable String empCode) {
        EmployeeResponse employee = employeeService.getEmployeeByCode(empCode);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER')")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody CreateEmployeeRequest request) {
        EmployeeResponse employee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER')")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, 
                                                          @RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse employee = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}