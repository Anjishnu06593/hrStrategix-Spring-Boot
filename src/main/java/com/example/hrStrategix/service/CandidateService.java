package com.example.hrStrategix.service;

import com.example.hrStrategix.dto.CandidateResponse;
import com.example.hrStrategix.dto.CreateCandidateRequest;
import com.example.hrStrategix.entity.Candidate;
import com.example.hrStrategix.entity.Employee;
import com.example.hrStrategix.entity.RecruitmentSource;
import com.example.hrStrategix.entity.User;
import com.example.hrStrategix.repository.CandidateRepository;
import com.example.hrStrategix.repository.EmployeeRepository;
import com.example.hrStrategix.repository.RecruitmentSourceRepository;
import com.example.hrStrategix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final RecruitmentSourceRepository recruitmentSourceRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public List<CandidateResponse> getAllCandidates() {
        return candidateRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CandidateResponse getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found with id: " + id));
        return mapToResponse(candidate);
    }

    public List<CandidateResponse> getCandidatesByStatus(String status) {
        return candidateRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CandidateResponse> getCandidatesByDateRange(LocalDate startDate, LocalDate endDate) {
        return candidateRepository.findByAppliedDateBetween(startDate, endDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CandidateResponse createCandidate(CreateCandidateRequest request) {
        Candidate candidate = Candidate.builder()
                .candidateName(request.getCandidateName())
                .candidateEmail(request.getCandidateEmail())
                .candidatePhone(request.getCandidatePhone())
                .appliedDate(request.getAppliedDate())
                .appliedPosition(request.getAppliedPosition())
                .status(request.getStatus())
                .experienceYears(request.getExperienceYears())
                .candidateExperienceScore(request.getCandidateExperienceScore())
                .costOfHire(request.getCostOfHire())
                .offerAccepted(request.getOfferAccepted())
                .hiredDate(request.getHiredDate())
                .feedback(request.getFeedback())
                .createdAt(OffsetDateTime.now())
                .build();

        // Set relationships
        if (request.getSourceId() != null) {
            RecruitmentSource source = recruitmentSourceRepository.findById(request.getSourceId())
                    .orElseThrow(() -> new IllegalArgumentException("Recruitment source not found"));
            candidate.setSource(source);
        }

        if (request.getRecruiterUserId() != null) {
            User recruiter = userRepository.findById(request.getRecruiterUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Recruiter not found"));
            candidate.setRecruiter(recruiter);
        }

        if (request.getHireEmployeeId() != null) {
            Employee hireEmployee = employeeRepository.findById(request.getHireEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Hire employee not found"));
            candidate.setHireEmployee(hireEmployee);
        }

        Candidate savedCandidate = candidateRepository.save(candidate);
        return mapToResponse(savedCandidate);
    }

    @Transactional
    public void deleteCandidate(Long id) {
        if (!candidateRepository.existsById(id)) {
            throw new IllegalArgumentException("Candidate not found with id: " + id);
        }
        candidateRepository.deleteById(id);
    }

    private CandidateResponse mapToResponse(Candidate candidate) {
        return CandidateResponse.builder()
                .id(candidate.getId())
                .candidateName(candidate.getCandidateName())
                .candidateEmail(candidate.getCandidateEmail())
                .candidatePhone(candidate.getCandidatePhone())
                .appliedDate(candidate.getAppliedDate())
                .appliedPosition(candidate.getAppliedPosition())
                .sourceName(candidate.getSource() != null ? candidate.getSource().getName() : null)
                .recruiterName(candidate.getRecruiter() != null ? candidate.getRecruiter().getFullName() : null)
                .status(candidate.getStatus())
                .experienceYears(candidate.getExperienceYears())
                .candidateExperienceScore(candidate.getCandidateExperienceScore())
                .costOfHire(candidate.getCostOfHire())
                .offerAccepted(candidate.getOfferAccepted())
                .hireEmployeeName(candidate.getHireEmployee() != null ? 
                    candidate.getHireEmployee().getFirstName() + " " + candidate.getHireEmployee().getLastName() : null)
                .hiredDate(candidate.getHiredDate())
                .feedback(candidate.getFeedback())
                .createdAt(candidate.getCreatedAt())
                .build();
    }
}