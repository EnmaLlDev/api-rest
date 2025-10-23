package com.fp.api_rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "treatmentid", nullable = false)
    private Treatment treatmentId;

    @Column(name = "totalamount", nullable = false)
    private double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issuedate", nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false)
    private Integer status; // 0: pendiente, 1: pagada, 2: cancelada
}
