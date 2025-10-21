package com.fp.api_rest.model;

import com.fp.api_rest.model.base.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "doctors")
public class Doctor extends Person {

    @Column(nullable = false, length = 20)
    private String license_number;

    @Column(length = 100)
    private String specialty;
}
