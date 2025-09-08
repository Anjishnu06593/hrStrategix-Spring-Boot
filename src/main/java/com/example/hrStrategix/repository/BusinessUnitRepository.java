package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {
    Optional<BusinessUnit> findByName(String name);
}