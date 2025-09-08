package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.RecruitmentSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentSourceRepository extends JpaRepository<RecruitmentSource, Long> {
    Optional<RecruitmentSource> findByName(String name);
}