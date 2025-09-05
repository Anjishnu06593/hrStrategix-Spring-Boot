package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "performance_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reviewed employee
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    /**
     * Reviewer (manager or lead)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee reviewer;

    @Column(name = "review_period_start")
    private LocalDate reviewPeriodStart;

    @Column(name = "review_period_end")
    private LocalDate reviewPeriodEnd;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "rating_scale", length = 50)
    private String ratingScale;

    @Column(columnDefinition = "text")
    private String comments;

    /**
     * User who created this review
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User createdBy;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
