package com.fp.api_rest.service;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dao.PatientDAO;
import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.model.dto.mapper.PatientMapper;
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

    public PatientDTO save(PatientDTO dto) {
        Patient patient = PatientMapper.toEntity(dto);
        Patient saved = patientDAO.save(patient);
        return PatientMapper.toDTO(saved);
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
