package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.AppointmentDTO;

/**
 * Clase de utilidad para mapear entre Appointment y AppointmentDTO.
 */
public class AppointmentMapper {

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     */
    private AppointmentMapper() {
        /* This utility class should not be instantiated */
    }

    /**
     * Convierte una entidad Appointment a AppointmentDTO.
     * @param appointment entidad a convertir
     * @return DTO equivalente
     */
    public static AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setPatient(appointment.getPatient() != null ? PatientMapper.toDTO(appointment.getPatient()) : null);
        dto.setDoctor(appointment.getDoctor() != null ? DoctorMapper.toDTO(appointment.getDoctor()) : null);
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    /**
     * Convierte un AppointmentDTO a entidad Appointment.
     * @param dto DTO a convertir
     * @return entidad equivalente
     */
    public static Appointment toEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setDateTime(dto.getDateTime());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());

        // Asignar las entidades Patient y Doctor directamente del DTO
        if (dto.getPatient() != null) {
            Patient patient = new Patient();
            patient.setId(dto.getPatient().getId());
            appointment.setPatient(patient);
        }
        if (dto.getDoctor() != null) {
            Doctor doctor = new Doctor();
            doctor.setId(dto.getDoctor().getId());
            appointment.setDoctor(doctor);
        }

        return appointment;
    }
}
