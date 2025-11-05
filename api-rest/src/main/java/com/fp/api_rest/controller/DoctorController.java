package com.fp.api_rest.controller;

import com.fp.api_rest.service.DoctorService;
import org.springframework.web.bind.annotation.*;
import com.fp.api_rest.model.dto.DoctorDTO;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/getAll")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public DoctorDTO getDoctorById(@PathVariable Integer id) {
        return doctorService.findById(id);
    }

    @PostMapping("/create")
    public DoctorDTO createDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.save(doctorDTO);
    }
/*
    @PutMapping
    public DoctorDTO updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.save(doctorDTO);
    }
*/
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteById(id);
    }

    @GetMapping("/specialty/{specialty}")
    public List<DoctorDTO> getDoctorsBySpecialty(@PathVariable String specialty) {
        return doctorService.findBySpecialty(specialty);
    }

    @GetMapping("/license/{licenseNumber}")
    public List<DoctorDTO> getDoctorsByLicenseNumber(@PathVariable String licenseNumber) {
        return doctorService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping("/search/{terms}")
    public List<DoctorDTO> getSpecialtyByTerms(@PathVariable String terms) {
        return doctorService.findBySpecialtyContainingIgnoreCase(terms);
    }
}