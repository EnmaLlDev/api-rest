package com.fp.api_rest.service;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.model.dto.mapper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public List<PatientDTO> getAllPatients() {
        return patientDAO.findAll()
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientDTO findById(Integer id) {
        return patientDAO.findById(id)
                .map(PatientMapper::toDTO)
                .orElse(null);
    }

    public Patient save(Patient patient) {
        return  patientDAO.save(patient);
    }

    public Patient update(Integer id, Patient data) {
        Patient existing = patientDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + id));

        // Actualización parcial: solo actualizar campos no vacíos
        if (data.getDni() != null && !data.getDni().isBlank()) {
            existing.setDni(data.getDni());
        }
        if (data.getFirstName() != null && !data.getFirstName().isBlank()) {
            existing.setFirstName(data.getFirstName());
        }
        if (data.getSecondName() != null && !data.getSecondName().isBlank()) {
            existing.setSecondName(data.getSecondName());
        }
        if (data.getLastName() != null && !data.getLastName().isBlank()) {
            existing.setLastName(data.getLastName());
        }
        if (data.getSecondLastName() != null && !data.getSecondLastName().isBlank()) {
            existing.setSecondLastName(data.getSecondLastName());
        }
        if (data.getEmail() != null && !data.getEmail().isBlank()) {
            existing.setEmail(data.getEmail());
        }
        if (data.getPhone() != null && !data.getPhone().isBlank()) {
            existing.setPhone(data.getPhone());
        }
        if (data.getBirthDate() != null) {
            existing.setBirthDate(data.getBirthDate());
        }
        if (data.getAddress() != null && !data.getAddress().isBlank()) {
            existing.setAddress(data.getAddress());
        }
        return patientDAO.save(existing);
    }

    public void deletePatient(Integer id) {
        patientDAO.deleteById(id);
    }

    public List<PatientDTO> findByAddressContaining(String address ) {
        return patientDAO.findByAddressContaining( address)
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }
}