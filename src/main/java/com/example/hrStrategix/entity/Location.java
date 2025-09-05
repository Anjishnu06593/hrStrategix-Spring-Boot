package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String region;

    @Column(name = "job_type", length = 20, nullable = false)
    private String jobType; // 'remote','hybrid','in-office' - DB enforces constraint

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}
