package com.example.hrStrategix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="levels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "sub_level", length = 1)
    private String subLevel; // DB has check constraint: NULL or single uppercase char

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
