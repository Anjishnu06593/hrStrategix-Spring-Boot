package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.PerformanceImprovementPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerformanceImprovementPlanRepository extends JpaRepository<PerformanceImprovementPlan, Long> {
    @Query("SELECT pip FROM PerformanceImprovementPlan pip WHERE pip.employee.id = :employeeId")
    List<PerformanceImprovementPlan> findByEmployeeId(@Param("employeeId") Long employeeId);
    
    List<PerformanceImprovementPlan> findByStatus(String status);
    
    @Query("SELECT pip FROM PerformanceImprovementPlan pip WHERE pip.planOwner.id = :planOwnerId")
    List<PerformanceImprovementPlan> findByPlanOwnerId(@Param("planOwnerId") Long planOwnerId);
}