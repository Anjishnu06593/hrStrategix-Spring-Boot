package com.example.hrStrategix.controller;

import com.example.hrStrategix.entity.*;
import com.example.hrStrategix.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/master-data")
@RequiredArgsConstructor
public class MasterDataController {
    private final LocationRepository locationRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final LevelRepository levelRepository;
    private final DesignationRepository designationRepository;
    private final JobFamilyRepository jobFamilyRepository;
    private final RecruitmentSourceRepository recruitmentSourceRepository;

    @GetMapping
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<Map<String, Object>> getAllMasterData() {
        Map<String, Object> masterData = new HashMap<>();
        
        List<Location> locations = locationRepository.findAll();
        List<BusinessUnit> businessUnits = businessUnitRepository.findAll();
        List<Level> levels = levelRepository.findAll();
        List<Designation> designations = designationRepository.findAll();
        List<JobFamily> jobFamilies = jobFamilyRepository.findAll();
        List<RecruitmentSource> recruitmentSources = recruitmentSourceRepository.findAll();
        
        masterData.put("locations", locations);
        masterData.put("businessUnits", businessUnits);
        masterData.put("levels", levels);
        masterData.put("designations", designations);
        masterData.put("jobFamilies", jobFamilies);
        masterData.put("recruitmentSources", recruitmentSources);
        
        return ResponseEntity.ok(masterData);
    }

    @GetMapping("/locations")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<Location>> getLocations() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

    @GetMapping("/business-units")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<BusinessUnit>> getBusinessUnits() {
        return ResponseEntity.ok(businessUnitRepository.findAll());
    }

    @GetMapping("/levels")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<Level>> getLevels() {
        return ResponseEntity.ok(levelRepository.findAll());
    }

    @GetMapping("/designations")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<Designation>> getDesignations() {
        return ResponseEntity.ok(designationRepository.findAll());
    }

    @GetMapping("/job-families")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD') or hasRole('LINE_MANAGER')")
    public ResponseEntity<List<JobFamily>> getJobFamilies() {
        return ResponseEntity.ok(jobFamilyRepository.findAll());
    }

    @GetMapping("/recruitment-sources")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('ADMIN') or hasRole('COUNTRY_MANAGER') or hasRole('BU_LEAD')")
    public ResponseEntity<List<RecruitmentSource>> getRecruitmentSources() {
        return ResponseEntity.ok(recruitmentSourceRepository.findAll());
    }
}