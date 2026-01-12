package com.fp.api_rest.controller;

import com.fp.api_rest.model.Doctor;
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

    @PutMapping("/update/{id}")
    public void updateDoctor(@PathVariable Integer id,@RequestBody DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        System.out.println("Doctor updated");
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteById(id);
    }

    @GetMapping("/license/{licenseNumber}")
    public List<DoctorDTO> getDoctorsByLicenseNumber(@PathVariable String licenseNumber) {
        return doctorService.findByLicenseNumber(licenseNumber);
    }

    @GetMapping("/search/{terms}")
    public List<DoctorDTO> findBySpecialtyContainingIgnoreCase(@PathVariable String terms) {
        return doctorService.findBySpecialtyContainingIgnoreCase(terms);
    }
}