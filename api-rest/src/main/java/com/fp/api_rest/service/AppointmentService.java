package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentMapper;
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

    public AppointmentDTO save(AppointmentDTO dto) {
        Appointment appointment = AppointmentMapper.toEntity(dto);
        Appointment saved = appointmentDAO.save(appointment);
        return AppointmentMapper.toDTO(saved);
    }

    public void deleteById(int id) {
        appointmentDAO.deleteById(id);
    }

}
