package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.JobFamily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobFamilyRepository extends JpaRepository<JobFamily, Long> {
    Optional<JobFamily> findByName(String name);
}