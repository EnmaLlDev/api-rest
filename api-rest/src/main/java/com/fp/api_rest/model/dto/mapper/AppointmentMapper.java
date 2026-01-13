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
        dto.setPatient(appointment.getPatient());
                PatientMapper.toDTO(appointment.getPatient());
        dto.setDoctor(appointment.getDoctor());
                DoctorMapper.toDTO(appointment.getDoctor());
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    public static Appointment toEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setDateTime(dto.getDateTime());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());
        Patient patient = new Patient();
            patient.setId(dto.getId());
            appointment.setPatient(patient);
        Doctor doctor = new Doctor();
            doctor.setId(dto.getId());
            appointment.setDoctor(doctor);
        return appointment;
    }
}
