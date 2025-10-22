package com.fp.api_rest.model.dto;

import com.fp.api_rest.model.StateTreatment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO {
    private Integer id;
    private String description;
    private double cost;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private StateTreatment status;
    private int patientId;
    private int doctorId;
}
