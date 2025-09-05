package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_code", nullable = false, unique = true)
    private String empCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String gender;

    private LocalDate dob;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "employment_type")
    private String employmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BusinessUnit businessUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Level level;

    @Column(name = "sub_level", length = 1)
    private String subLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Designation designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_family_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private JobFamily jobFamily;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_lead_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee unitLead;

    @Column(name = "gender_identity")
    private String genderIdentity;

    @Column(name = "prior_experience_years", precision = 6, scale = 2)
    private BigDecimal priorExperienceYears;

    @Column(name = "msg_global_india_exp", precision = 6, scale = 2)
    private BigDecimal msgGlobalIndiaExp;

    @Column(name = "total_relevant_exp", precision = 8, scale = 2, insertable = false, updatable = false)
    private BigDecimal totalRelevantExp;

    private String email;
    private String phone;

    @Column(name = "permanent_wfh")
    private Boolean permanentWfh;

    @Column(name = "work_location_description")
    private String workLocationDescription;

    private String status;

    @Column(name = "date_of_resignation")
    private LocalDate dateOfResignation;

    @Column(name = "relieved_as_on")
    private LocalDate relievedAsOn;

    @Column(name = "reason_of_separation", columnDefinition = "text")
    private String reasonOfSeparation;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
