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
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String dni;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstName;

    @Column(name = "secondname", length = 50)
    private String secondName;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastName;

    @Column(name = "secondlastname", length = 50)
    private String secondLastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String phone;
}
