package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.AppointmentDetail;
import com.fp.api_rest.model.dto.DetailsDTO;
import com.fp.api_rest.model.dto.mapper.AppointmentDetailMapper;
import com.fp.api_rest.repository.dao.AppointmentDAO;
import com.fp.api_rest.repository.dao.AppointmentDetailDAO;
import com.fp.api_rest.repository.dao.PatientDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DetailsService {

    private final AppointmentDetailDAO appointmentDetailDAO;
    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;

    public DetailsService(AppointmentDetailDAO appointmentDetailDAO, AppointmentDAO appointmentDAO, PatientDAO patientDAO) {
        this.appointmentDetailDAO = appointmentDetailDAO;
        this.appointmentDAO = appointmentDAO;
        this.patientDAO = patientDAO;
    }

    public List<DetailsDTO> findAll() {
        return appointmentDetailDAO.findAll()
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DetailsDTO findById(Integer id) {
        return appointmentDetailDAO.findById(id)
                .map(AppointmentDetailMapper::toDTO)
                .orElse(null);
    }

    public List<DetailsDTO> findByAppointmentId(Integer appointmentId) {
        return appointmentDetailDAO.findByAppointmentId(appointmentId)
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

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

    public void deleteById(Integer id) {
        if (!appointmentDetailDAO.existsById(id)) {
            throw new EntityNotFoundException("AppointmentDetail not found with id " + id);
        }
        appointmentDetailDAO.deleteById(id);
    }

    public List<DetailsDTO> findByPatientEmail(String email) {
        return patientDAO.findByEmail(email)
                .map(patient -> appointmentDetailDAO.findByPatientId(patient.getId()))
                .orElse(List.of())
                .stream()
                .map(AppointmentDetailMapper::toDTO)
                .collect(Collectors.toList());
    }
}
