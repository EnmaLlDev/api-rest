package com.fp.api_rest.service;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public Patient save(Patient patient) {return patientRepository.save(patient);}

    @Transactional(readOnly = true)
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Transactional
    public void deletePatient(int id) {
        patientRepository.deleteById(id);}

    @Transactional
    public Patient updatePatient(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            return patientRepository.save(patient);
        } else {
            throw new IllegalArgumentException("Doctor with id " + patient.getId() + " does not exist.");
        }
    }
}
