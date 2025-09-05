package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "performance_improvement_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceImprovementPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Employee on the PIP
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    /**
     * Plan owner (HR/Admin/User who created the PIP)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_owner_user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User planOwner;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(columnDefinition = "text")
    private String objectives;

    @Column(columnDefinition = "text")
    private String actions;

    @Column(length = 50)
    private String status; // open, closed, extended etc.

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
