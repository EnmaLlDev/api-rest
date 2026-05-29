package com.fp.api_rest.model;

import com.fp.api_rest.model.base.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "doctors")
/**
 * Entidad JPA que representa un médico de la clínica.
 */
public class Doctor extends Person {

    @Column(name = "licenseNumber", nullable = false, length = 20)
    private String licenseNumber;

    @Column(name = "specialty", length = 100)
    private String specialty;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Appointment> appointments;

}
