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
    private String first_name;
    private String second_name;
    private String last_name;
    private String second_last_name;
    private String email;
    private String phone;
    private LocalDate birth_date;
    private String address;
}
