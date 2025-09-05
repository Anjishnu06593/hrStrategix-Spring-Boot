package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "job_families")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
