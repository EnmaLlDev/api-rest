package com.fp.api_rest.controller;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/getAll")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/get/{id}")
    public PatientDTO getPatientById(@PathVariable int id) {
        return patientService.findById(id);
    }

    @PostMapping("/create")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@PathVariable Integer id,@RequestBody Patient patient) {
        patientService.update(id, patient);
        System.out.println("Patient updated");
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/address/{address}")
    public List<PatientDTO> getPatientsByAddress(@PathVariable("address") String address) {
        return patientService.findByAddressContaining(address);
    }
}
