package com.fp.api_rest.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;
    private String licenseNumber;
    private String specialty;
    private List<Integer> patientIds;
}