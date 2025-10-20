package com.fp.api_rest.controller;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService _doctorService;

    @GetMapping("/getAll")
    public List<Doctor> getAllDoctors() {
        return _doctorService.getAllDoctors();
    }

    @GetMapping("/getFirstNames")
    public ResponseEntity<List<String>> getAllNames() {
        List<String> names = _doctorService.getAllFirstNames();
        if (names.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(names);
    }

    @GetMapping("/getNames")
    public ResponseEntity<String> getNames() {
        String names = _doctorService.getAllNames();
        if (names.isBlank()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(names);
    }

    @PostMapping("/create")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return _doctorService.save(doctor);
    }

    @PutMapping("/update/{id}")
    public void updateDoctor(@RequestBody Doctor doctor) {
        _doctorService.updateDoctor(doctor);
        System.out.println("Updated, doctor id  " + doctor.getId());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        _doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();

    }
}
