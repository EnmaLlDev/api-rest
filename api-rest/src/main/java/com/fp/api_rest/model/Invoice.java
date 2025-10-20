package com.fp.api_rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment_id;

    @Column(name = "total_amount", nullable = false)
    private double total_amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issue_date;

    @Column(nullable = false)
    private int status; // 0: pendiente, 1: pagada, 2: cancelada
}
