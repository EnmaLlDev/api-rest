package com.fp.api_rest.controller;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.dao.AppointmentDAO;
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

    @PostMapping( "/create")
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointment) {
        return appointmentService.save(appointment);
    }

    @PutMapping("/update/{id}")
    public AppointmentDTO updatePatient(@RequestBody AppointmentDTO appointment) {
        return appointmentService.save(appointment);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        appointmentService.deleteById(id);
    }
}