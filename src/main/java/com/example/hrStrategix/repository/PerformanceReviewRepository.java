package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    @Query("SELECT pr FROM PerformanceReview pr WHERE pr.employee.id = :employeeId")
    List<PerformanceReview> findByEmployeeId(@Param("employeeId") Long employeeId);
    
    @Query("SELECT pr FROM PerformanceReview pr WHERE pr.reviewer.id = :reviewerId")
    List<PerformanceReview> findByReviewerId(@Param("reviewerId") Long reviewerId);
    
    @Query("SELECT pr FROM PerformanceReview pr WHERE pr.reviewDate BETWEEN :startDate AND :endDate")
    List<PerformanceReview> findByReviewDateBetween(@Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);
}