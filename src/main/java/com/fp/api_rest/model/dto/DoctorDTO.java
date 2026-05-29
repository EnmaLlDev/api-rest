package com.fp.api_rest.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO que representa un médico para transferencia de datos.
 */
public class DoctorDTO {
    private Integer id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private String email;
    private String phone;
    private String dni;
    private String licenseNumber;
    private String specialty;
}