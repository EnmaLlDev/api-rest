package com.fp.api_rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment_details")
public class AppointmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentId", nullable = false, unique = true)
    private Appointment appointment;

    @Column(length = 1000)
    private String diagnosis;

    @Column(length = 1000)
    private String prescription;

    @Column(length = 2000)
    private String notes;

    @Column(length = 500)
    private String treatment;

    @Column(length = 500)
    private String followUp;
}
