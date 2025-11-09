package com.fp.api_rest.service;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientCsvService {
    private final PatientDAO patientDAO; // será la implementación CSV con el perfil activo

    public PatientCsvService(PatientDAO patientDAO) { this.patientDAO = patientDAO; }

    public List<Patient> getAll() { return patientDAO.findAll(); }
    public List<Patient> searchByAddress(String q) { return patientDAO.findByAddressContaining(q); }
    public Patient upsert(Patient p) { return patientDAO.save(p); }
    public List<Patient> importAll(List<Patient> list) { return patientDAO.saveAll(list); }
    public void deleteAll() { patientDAO.deleteAll(); }
}