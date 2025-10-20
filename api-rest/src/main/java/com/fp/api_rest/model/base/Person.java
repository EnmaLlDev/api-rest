package com.fp.api_rest.model.base;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 20)
    private String dni;

    @Column(name = "first_name", nullable = false, length = 50)
    private String first_name;

    @Column(name = "second_name", length = 50)
    private String second_name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String last_name;

    @Column(name = "second_last_name", length = 50)
    private String second_last_name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String phone;
}
