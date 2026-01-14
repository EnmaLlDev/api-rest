package com.fp.api_rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailDTO {
    private Integer id;
    private Integer appointmentId;
    private String diagnosis;
    private String prescription;
    private String notes;
    private String treatment;
    private String followUp;
}

