package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.AppointmentDetail;
import com.fp.api_rest.model.dto.AppointmentDetailDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentDetailMapper;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.repository.dao.AppointmentDetailDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentDetailService {

    private final AppointmentDetailDAO appointmentDetailDAO;
    private final AppointmentDAO appointmentDAO;

    public AppointmentDetailService(AppointmentDetailDAO appointmentDetailDAO, AppointmentDAO appointmentDAO) {
        this.appointmentDetailDAO = appointmentDetailDAO;
        this.appointmentDAO = appointmentDAO;
    }

    public List<AppointmentDetailDTO> findAll() {
        return appointmentDetailDAO.findAll()
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDetailDTO findById(Integer id) {
        return appointmentDetailDAO.findById(id)
                .map(AppointmentDetailMapper::toDTO)
                .orElse(null);
    }

    public AppointmentDetailDTO findByAppointmentId(Integer appointmentId) {
        return appointmentDetailDAO.findByAppointmentId(appointmentId)
                .map(AppointmentDetailMapper::toDTO)
                .orElse(null);
    }

    public AppointmentDetailDTO save(AppointmentDetailDTO dto) {

        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentDAO.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + dto.getAppointmentId()));


            if (appointmentDetailDAO.findByAppointmentId(dto.getAppointmentId()).isPresent()) {
                throw new IllegalStateException("Appointment detail already exists for appointment with id " + dto.getAppointmentId());
            }

            AppointmentDetail detail = AppointmentDetailMapper.toEntity(dto);
            detail.setAppointment(appointment);

            AppointmentDetail saved = appointmentDetailDAO.save(detail);
            return AppointmentDetailMapper.toDTO(saved);
        }

        throw new IllegalArgumentException("Appointment ID is required");
    }

    public AppointmentDetailDTO update(Integer id, AppointmentDetailDTO dto) {
        AppointmentDetail existing = appointmentDetailDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppointmentDetail not found with id " + id));

        // Actualización parcial: solo actualizar campos no vacíos
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

    public void deleteById(Integer id) {
        if (!appointmentDetailDAO.existsById(id)) {
            throw new EntityNotFoundException("AppointmentDetail not found with id " + id);
        }
        appointmentDetailDAO.deleteById(id);
    }
}
