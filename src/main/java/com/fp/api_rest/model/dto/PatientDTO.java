package com.fp.api_rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private int id;
    private String dni;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String address;
}
