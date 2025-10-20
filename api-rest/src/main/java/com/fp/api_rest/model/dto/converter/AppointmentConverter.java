package com.fp.api_rest.model.dto.converter;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.AppointmentDTO;

public class AppointmentConverter {

    public static AppointmentDTO toDTO(Appointment appointment) {
        if (appointment == null) return null;

        return AppointmentDTO.builder()
                .id(appointment.getId())
                .date_time(appointment.getDate_time())
                .patient_id(appointment.getPatient_id() != null ?
                        appointment.getPatient_id().getId() : 0)
                .patient_name(appointment.getPatient_id() != null ?
                        appointment.getPatient_id().getFirst_name() + " " + appointment.getPatient_id().getLast_name() : null)
                .doctor_id(appointment.getDoctor_id() != null ?
                        appointment.getDoctor_id().getId() : 0)
                .doctor_name(appointment.getDoctor_id() != null ?
                        appointment.getDoctor_id().getFirst_name() + " " + appointment.getDoctor_id().getLast_name() : null)
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .build();
    }

    public static Appointment toEntity(AppointmentDTO dto) {
        if (dto == null) return null;
        return Appointment.builder()
                .id(dto.getId())
                .date_time(dto.getDate_time())
                .patient_id(dto.getPatient_id() != 0 ?
                        Patient.builder().id(dto.getPatient_id()).build() : null)
                .doctor_id(dto.getDoctor_id() != 0 ?
                        Doctor.builder().id(dto.getDoctor_id()).build() : null)
                .reason(dto.getReason())
                .status(dto.getStatus())
                .build();
    }
}
