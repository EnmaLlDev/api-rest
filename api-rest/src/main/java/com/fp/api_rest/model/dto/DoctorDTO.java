package com.fp.api_rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DoctorDTO {
    private Integer id;              // acepta nulos
    private String firstName;
    private String lastName;
    private String dni;
    private String licenseNumber;
    private String specialty;
}