package com.fp.api_rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "patientid", nullable = false)
    private Patient patientId;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctor doctorId;

    @Column(length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StateAppointment status = StateAppointment.NO_ATTEND;
}

