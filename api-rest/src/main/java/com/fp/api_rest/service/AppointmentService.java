package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
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

    public Appointment update(Integer id, AppointmentDTO data) {
        Appointment existing = appointmentDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appoinment not found with id " + id));

        // Actualización parcial: solo actualizar campos no vacíos
        if (data.getDateTime() != null) {
            existing.setDateTime(data.getDateTime());
        }
        if (data.getPatient() != null) {
            existing.setPatient(data.getPatient());
        }
        if (data.getDoctor() != null) {
            existing.setDoctor(data.getDoctor());
        }
        if (data.getReason() != null) {
            existing.setReason(data.getReason());
        }
        if (data.getStatus() != null) {
            existing.setStatus(data.getStatus());
        }
        return appointmentDAO.save(existing);
    }

    public AppointmentDTO save(AppointmentDTO dto) {
        Appointment appointment = AppointmentMapper.toEntity(dto);
        Appointment saved = appointmentDAO.save(appointment);
        return AppointmentMapper.toDTO(saved);
    }

    public void deleteById(int id) {
        appointmentDAO.deleteById(id);
    }
}
