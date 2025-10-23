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
    @JoinColumn(name = "patientid", nullable = false)
    private Patient patientId;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctor doctorId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private double cost;

    @Column(name = "startdate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "enddate")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StateTreatment status=StateTreatment.PAUSED;
}

