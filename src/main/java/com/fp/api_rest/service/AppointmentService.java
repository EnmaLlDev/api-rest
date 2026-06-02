package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.repository.dao.DoctorDAO;
import com.fp.api_rest.repository.dao.PatientDAO;
import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de citas médicas.
 */
@Service
public class AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;

    public AppointmentService(AppointmentDAO appointmentDAO, PatientDAO patientDAO, DoctorDAO doctorDAO) {
        this.appointmentDAO = appointmentDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
    }

    /**
     * Lista todas las citas.
     * @return lista de citas como DTO
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        return appointmentDAO.findAll()
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca una cita por su ID.
     * @param id identificador de la cita
     * @return DTO de la cita o null si no existe
     */
    @Transactional(readOnly = true)
    public AppointmentDTO findById(int id) {
        return appointmentDAO.findById(id)
                .map(AppointmentMapper::toDTO)
                .orElse(null);
    }

    /**
     * Actualiza una cita existente de forma parcial.
     * @param id identificador de la cita
     * @param data datos a actualizar
     * @return DTO de la cita actualizada
     */
    @Transactional
    public AppointmentDTO update(Integer id, AppointmentDTO data) {
        Appointment existing = appointmentDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + id));

        if (data.getDateTime() != null) {
            existing.setDateTime(data.getDateTime());
        }
        if (data.getPatient() != null) {
            Patient patient = new Patient();
            patient.setId(data.getPatient().getId());
            existing.setPatient(patient);
        }
        if (data.getDoctor() != null) {
            Doctor doctor = new Doctor();
            doctor.setId(data.getDoctor().getId());
            existing.setDoctor(doctor);
        }
        if (data.getReason() != null) {
            existing.setReason(data.getReason());
        }
        if (data.getStatus() != null) {
            existing.setStatus(data.getStatus());
        }
        Appointment saved = appointmentDAO.save(existing);
        return AppointmentMapper.toDTO(saved);
    }

    /**
     * Crea una nueva cita.
     * @param dto datos de la cita
     * @return DTO de la cita creada
     */
    @Transactional
    public AppointmentDTO save(AppointmentDTO dto) {
        Appointment appointment = AppointmentMapper.toEntity(dto);
        Appointment saved = appointmentDAO.save(appointment);
        return AppointmentMapper.toDTO(saved);
    }

    /**
     * Elimina una cita por su ID.
     * @param id identificador de la cita
     */
    @Transactional
    public void deleteById(int id) {
        appointmentDAO.deleteById(id);
    }

    /**
     * Busca citas por motivo.
     * @param reason motivo de la cita
     * @return lista de citas coincidentes
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByReason(String reason) {
        return appointmentDAO.findByReason(reason)
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca citas por estado.
     * @param status estado de la cita
     * @return lista de citas en ese estado
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByStatus(String status) {
        return appointmentDAO.findByStatus(Enum.valueOf(com.fp.api_rest.model.StateAppointment.class, status))
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca las citas asociadas a un paciente por su email.
     * @param email email del paciente
     * @return lista de citas del paciente
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByPatientEmail(String email) {
        return patientDAO.findByEmail(email)
                .map(patient -> appointmentDAO.findByPatientId(patient.getId()))
                .orElse(List.of())
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca las citas de un doctor por su ID (Doctor.id, tipo Integer).
     * @param doctorId identificador del doctor
     * @return lista de citas del doctor
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDoctorId(Integer doctorId) {
        return appointmentDAO.findByDoctorId(doctorId)
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Resuelve el email del doctor autenticado a su Doctor.id y devuelve sus citas.
     * Flujo: email (User.username) → Doctor.email → Doctor.id → AppointmentDAO.findByDoctorId.
     * @param email email del doctor autenticado (authentication.getName())
     * @return lista de citas del doctor, o vacía si el doctor no existe
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDoctorForEmail(String email) {
        return doctorDAO.findByEmail(email)
                .map(doctor -> findByDoctorId(doctor.getId()))
                .orElse(List.of());
    }
}