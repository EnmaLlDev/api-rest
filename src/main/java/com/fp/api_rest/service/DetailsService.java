package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.AppointmentDetail;
import com.fp.api_rest.model.dto.DetailsDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentDetailMapper;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.repository.dao.AppointmentDetailDAO;
import com.fp.api_rest.repository.dao.DoctorDAO;
import com.fp.api_rest.repository.dao.PatientDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de los detalles clínicos de las citas.
 */
@Service
@Transactional
public class DetailsService {

    private final AppointmentDetailDAO appointmentDetailDAO;
    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;

    public DetailsService(AppointmentDetailDAO appointmentDetailDAO, AppointmentDAO appointmentDAO,
                          PatientDAO patientDAO, DoctorDAO doctorDAO) {
        this.appointmentDetailDAO = appointmentDetailDAO;
        this.appointmentDAO = appointmentDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
    }

    /**
     * Lista todos los detalles clínicos.
     * @return lista de detalles como DTO
     */
    public List<DetailsDTO> findAll() {
        return appointmentDetailDAO.findAll()
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un detalle clínico por su ID.
     * @param id identificador del detalle
     * @return DTO del detalle o null si no existe
     */
    public DetailsDTO findById(Integer id) {
        return appointmentDetailDAO.findById(id)
                .map(AppointmentDetailMapper::toDTO)
                .orElse(null);
    }

    /**
     * Busca detalles clínicos por ID de cita.
     * @param appointmentId identificador de la cita
     * @return lista de detalles asociados
     */
    public List<DetailsDTO> findByAppointmentId(Integer appointmentId) {
        return appointmentDetailDAO.findByAppointmentId(appointmentId)
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo detalle clínico asociado a una cita.
     * @param dto datos del detalle
     * @return DTO del detalle creado
     */
    public DetailsDTO save(DetailsDTO dto) {
        if (dto.getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment ID is required");
        }

        Appointment appointment = appointmentDAO.findById(dto.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + dto.getAppointmentId()));

        AppointmentDetail detail = AppointmentDetailMapper.toEntity(dto);
        detail.setAppointment(appointment);

        AppointmentDetail saved = appointmentDetailDAO.save(detail);
        return AppointmentDetailMapper.toDTO(saved);
    }

    /**
     * Actualiza un detalle clínico de forma parcial.
     * @param id identificador del detalle
     * @param dto datos a actualizar
     * @return DTO del detalle actualizado
     */
    public DetailsDTO update(Integer id, DetailsDTO dto) {
        AppointmentDetail existing = appointmentDetailDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppointmentDetail not found with id " + id));

        if (dto.getDiagnosis() != null) {
            existing.setDiagnosis(dto.getDiagnosis());
        }
        if (dto.getPrescription() != null) {
            existing.setPrescription(dto.getPrescription());
        }
        if (dto.getNotes() != null) {
            existing.setNotes(dto.getNotes());
        }
        if (dto.getTreatment() != null) {
            existing.setTreatment(dto.getTreatment());
        }
        if (dto.getFollowUp() != null) {
            existing.setFollowUp(dto.getFollowUp());
        }

        AppointmentDetail saved = appointmentDetailDAO.save(existing);
        return AppointmentDetailMapper.toDTO(saved);
    }

    /**
     * Elimina un detalle clínico por su ID.
     * @param id identificador del detalle
     */
    public void deleteById(Integer id) {
        if (!appointmentDetailDAO.existsById(id)) {
            throw new EntityNotFoundException("AppointmentDetail not found with id " + id);
        }
        appointmentDetailDAO.deleteById(id);
    }

    /**
     * Busca detalles clínicos por email del paciente.
     * @param email email del paciente
     * @return lista de detalles del paciente
     */
    public List<DetailsDTO> findByPatientEmail(String email) {
        return patientDAO.findByEmail(email)
                .map(patient -> appointmentDetailDAO.findByPatientId(patient.getId()))
                .orElse(List.of())
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca detalles clínicos de las citas de un doctor por su ID (Doctor.id, tipo Integer).
     * @param doctorId identificador del doctor
     * @return lista de detalles del doctor
     */
    public List<DetailsDTO> findByDoctorId(Integer doctorId) {
        return appointmentDetailDAO.findByDoctorId(doctorId)
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Resuelve el email del doctor autenticado a su Doctor.id y devuelve sus detalles.
     * Flujo: email (User.username) → Doctor.email → Doctor.id → AppointmentDetailDAO.findByDoctorId.
     * @param email email del doctor autenticado (authentication.getName())
     * @return lista de detalles del doctor, o vacía si el doctor no existe
     */
    public List<DetailsDTO> findByDoctorForEmail(String email) {
        return doctorDAO.findByEmail(email)
                .map(doctor -> findByDoctorId(doctor.getId()))
                .orElse(List.of());
    }
}
