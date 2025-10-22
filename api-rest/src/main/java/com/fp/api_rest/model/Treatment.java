package com.fp.api_rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
    private Patient patientId;

    @ManyToOne
    @JoinColumn(name = "doctorId", nullable = false)
    private Doctor doctorId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column
    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StateTreatment status=StateTreatment.PAUSED;
}

