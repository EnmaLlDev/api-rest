package com.fp.api_rest.model;

import com.fp.api_rest.model.base.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "patients")
public class Patient extends Person {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(length = 200)
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Appointment> appointments;
}
