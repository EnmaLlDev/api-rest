package com.fp.api_rest.service;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dao.PatientDAO;
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
        existing.setDni(data.getDni());
        existing.setFirstName(data.getFirstName());
        existing.setSecondName(data.getSecondName());
        existing.setLastName(data.getLastName());
        existing.setSecondLastName(data.getSecondLastName());
        existing.setEmail(data.getEmail());
        existing.setPhone(data.getPhone());
        existing.setBirthDate(data.getBirthDate());
        existing.setAddress(data.getAddress());
        return patientDAO.save(existing);
    }

    public void deletePatient(Integer id) {
        patientDAO.deleteById(id);
    }

    public List<PatientDTO> findByAddress(String address ) {
        return patientDAO.findByAddress( address)
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

}
