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
    private LocalDateTime dateTime;
    private int patientDTO;
    private int doctorDTO;
    private String reason;
    private StateAppointment status;
}
