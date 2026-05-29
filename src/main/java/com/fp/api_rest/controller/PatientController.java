package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.save(patientDTO);
    }

    @PutMapping("/update/{id}")
    public PatientDTO updatePatient(@PathVariable Integer id, @RequestBody PatientDTO patientDTO) {
        return patientService.update(id, patientDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/address/{address}")
    public List<PatientDTO> getPatientsByAddress(@PathVariable("address") String address) {
        return patientService.findByAddressContaining(address);
    }

    @GetMapping("/me")
    public ResponseEntity<PatientDTO> getMyData(Authentication authentication) {
        String email = authentication.getName();
        PatientDTO patient = patientService.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }
}
