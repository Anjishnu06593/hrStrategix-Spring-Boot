package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "recruitment_channel_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentChannelMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_channel_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RecruitmentSource recruitmentChannel;

    @Column(name = "metric_date", nullable = false)
    private LocalDate metricDate;

    @Column(name = "cost_spent", precision = 12, scale = 2)
    private BigDecimal costSpent;

    @Column(name = "hires_count")
    private Integer hiresCount;

    @Column(name = "avg_time_to_hire_days", precision = 8, scale = 2)
    private BigDecimal avgTimeToHireDays;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(columnDefinition = "jsonb")
    private String metadata;
}
