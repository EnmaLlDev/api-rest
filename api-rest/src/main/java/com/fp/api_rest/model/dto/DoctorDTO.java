package com.fp.api_rest.model.dto;
import lombok.Data;

@Data
public class DoctorDTO {
    private Integer id;              // acepta nulos
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String dni;
    private String licenseNumber;
    private String specialty;
}