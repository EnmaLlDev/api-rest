package com.fp.api_rest.model.base;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
/**
 * Clase base abstracta para entidades de personas (pacientes, médicos, etc.).
 */
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String dni;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String secondName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 50)
    private String secondLastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String phone;
}
