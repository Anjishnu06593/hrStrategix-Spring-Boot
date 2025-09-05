package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Candidate relation (mandatory)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Candidate candidate;

    @Column(name = "offered_salary", precision = 12, scale = 2)
    private BigDecimal offeredSalary;

    @Column(name = "start_date")
    private LocalDate startDate;

    private Boolean acceptance;

    @Column(name = "accepted_on")
    private LocalDate acceptedOn;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
