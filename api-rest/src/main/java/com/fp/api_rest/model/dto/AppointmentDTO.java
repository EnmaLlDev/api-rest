package com.fp.api_rest.model.dto;

import com.fp.api_rest.model.StateAppointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private int id;
    private LocalDateTime date_time;
    private int patient_id;
    private String patient_name;
    private int doctor_id;
    private String doctor_name;
    private String reason;
    private StateAppointment status;
}
