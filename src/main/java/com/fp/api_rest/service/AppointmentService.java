package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.repository.dao.PatientDAO;
import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;

    public AppointmentService(AppointmentDAO appointmentDAO, PatientDAO patientDAO) {
        this.appointmentDAO = appointmentDAO;
        this.patientDAO = patientDAO;
    }

    public List<AppointmentDTO> findAll() {
        return appointmentDAO.findAll()
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO findById(int id) {
        return appointmentDAO.findById(id)
                .map(AppointmentMapper::toDTO)
                .orElse(null);
    }

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

    public AppointmentDTO save(AppointmentDTO dto) {
        Appointment appointment = AppointmentMapper.toEntity(dto);
        Appointment saved = appointmentDAO.save(appointment);
        return AppointmentMapper.toDTO(saved);
    }

    public void deleteById(int id) {
        appointmentDAO.deleteById(id);
    }

    public List<AppointmentDTO> findByReason(String reason) {
        return appointmentDAO.findByReason(reason)
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> findByStatus(String status) {
        return appointmentDAO.findByStatus(Enum.valueOf(com.fp.api_rest.model.StateAppointment.class, status))
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> findByPatientEmail(String email) {
        return patientDAO.findByEmail(email)
                .map(patient -> appointmentDAO.findByPatientId(patient.getId()))
                .orElse(List.of())
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}