package com.fp.api_rest.controller;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService _patientService;

    @GetMapping("/getAll")
    public List<PatientDTO> getAllPatients() {
        return _patientService.getAllPatients();
    }

    @GetMapping("/get/{id}")
    public PatientDTO getPatientById(@PathVariable int id) {
        return _patientService.findById(id);
    }

    @PostMapping("/create")
    public Patient createPatient(@RequestBody Patient patient) {
        return _patientService.save(patient);
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@PathVariable Integer id,@RequestBody Patient patient) {
        _patientService.update(id, patient);
        System.out.println("Patient updated");
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        _patientService.deletePatient(id);
    }

    @GetMapping("/adress/{address}")
    public List<PatientDTO> getPatientsByAddress(@PathVariable String address) {
        return _patientService.findByAddress(address);
    }
}
