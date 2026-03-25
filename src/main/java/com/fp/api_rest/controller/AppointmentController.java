package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAll")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable int id) {
        AppointmentDTO dto = appointmentService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointment) {
        AppointmentDTO created = appointmentService.save(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointment) {
        try {
            AppointmentDTO updated = appointmentService.update(id, appointment);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}