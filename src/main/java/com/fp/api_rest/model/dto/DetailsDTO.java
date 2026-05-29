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
 * DTO que representa el detalle clínico de una cita para transferencia de datos.
 */
public class DetailsDTO {
    private Integer id;
    private Integer appointmentId;
    private String diagnosis;
    private String prescription;
    private String notes;
    private String treatment;
    private String followUp;
}

