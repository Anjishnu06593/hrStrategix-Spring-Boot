package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByStatus(String status);
    
    @Query("SELECT c FROM Candidate c WHERE c.appliedDate BETWEEN :startDate AND :endDate")
    List<Candidate> findByAppliedDateBetween(@Param("startDate") LocalDate startDate, 
                                           @Param("endDate") LocalDate endDate);
    
    @Query("SELECT c FROM Candidate c WHERE c.recruiter.id = :recruiterId")
    List<Candidate> findByRecruiterId(@Param("recruiterId") Long recruiterId);
    
    @Query("SELECT c FROM Candidate c WHERE c.source.id = :sourceId")
    List<Candidate> findBySourceId(@Param("sourceId") Long sourceId);
}