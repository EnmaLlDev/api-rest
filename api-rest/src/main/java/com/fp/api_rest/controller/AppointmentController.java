package com.fp.api_rest.controller;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @GetMapping("/getAll")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable int id) {
        return appointmentService.findById(id);
    }

    @PostMapping( "/create")
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointment) {
        return appointmentService.save(appointment);
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@RequestBody AppointmentDTO appointment) {
        appointmentService.save(appointment);
        System.out.println("Updated, doctor id  " + appointment.getId());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}