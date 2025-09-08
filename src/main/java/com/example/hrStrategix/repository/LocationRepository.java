package com.example.hrStrategix.repository;

import com.example.hrStrategix.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByCity(String city);
    List<Location> findByCountry(String country);
    List<Location> findByJobType(String jobType);
}