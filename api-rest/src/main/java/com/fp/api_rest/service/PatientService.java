package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        return patients;
    }

    public Patient save(Patient patient) {
        return patient;
    }
}
