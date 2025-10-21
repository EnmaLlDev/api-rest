package com.fp.api_rest.controller;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService _patientService;

    @GetMapping("/getAll")
    public List<Patient> getAllPatients() {
        return _patientService.findAll();
    }

    @PostMapping("/create")
    public Patient createPatient(@RequestBody Patient patient) {
        return _patientService.save(patient);
    }
}
