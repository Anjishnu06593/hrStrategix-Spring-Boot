package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_name", nullable = false, length = 255)
    private String candidateName;

    @Column(name = "candidate_email", length = 255)
    private String candidateEmail;

    @Column(name = "candidate_phone", length = 50)
    private String candidatePhone;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "applied_position", length = 200)
    private String appliedPosition;

    /**
     * Recruitment source relation
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RecruitmentSource source;

    /**
     * Recruiter (user)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User recruiter;

    private String status; // applied, screened, interviewed, offered, hired, rejected

    @Column(name = "experience_years", precision = 6, scale = 2)
    private BigDecimal experienceYears;

    @Column(name = "candidate_experience_score", precision = 5, scale = 2)
    private BigDecimal candidateExperienceScore;

    @Column(name = "cost_of_hire", precision = 12, scale = 2)
    private BigDecimal costOfHire;

    @Column(name = "offer_accepted")
    private Boolean offerAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hire_employee_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee hireEmployee;

    @Column(name = "hired_date")
    private LocalDate hiredDate;

    @Column(columnDefinition = "text")
    private String feedback;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
