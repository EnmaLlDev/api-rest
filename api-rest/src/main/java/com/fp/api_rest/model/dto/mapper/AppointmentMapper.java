package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.dto.AppointmentDTO;

public class AppointmentMapper {

    public static AppointmentDTO toDTO (Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setPatient(appointment.getPatient());
        dto.setDoctor(appointment.getDoctor());
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    public static Appointment toEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setDateTime(dto.getDateTime());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());

        // Asignar las entidades Patient y Doctor directamente del DTO
        if (dto.getPatient() != null) {
            appointment.setPatient(dto.getPatient());
        }
        if (dto.getDoctor() != null) {
            appointment.setDoctor(dto.getDoctor());
        }

        return appointment;
    }
}
