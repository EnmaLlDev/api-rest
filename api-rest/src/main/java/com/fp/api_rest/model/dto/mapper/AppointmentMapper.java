package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.AppointmentDTO;

public class AppointmentMapper {

    public static AppointmentDTO toDTO (Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setPatientDTO(appointment.getPatientId() != null ?
                PatientMapper.toDTO(appointment.getPatientId()).getId() : null);
        dto.setDoctorDTO(appointment.getDoctorId() != null ?
                DoctorMapper.toDTO(appointment.getDoctorId()).getId() : null);
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    public static Appointment toEntity(Appointment dto) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setDateTime(dto.getDateTime());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());
        Patient patient = new Patient();
            patient.setId(dto.getPatientId().getId());
            appointment.setPatientId(patient);
        Doctor doctor = new Doctor();
            doctor.setId(dto.getDoctorId().getId());
            appointment.setDoctorId(doctor);
        return appointment;
    }
}
