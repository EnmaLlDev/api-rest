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
public class DiagnosticDTO {
    private Integer id;
    private String description;
    private LocalDate date;
    private DoctorDTO doctor;
    private PatientDTO patient;
}
