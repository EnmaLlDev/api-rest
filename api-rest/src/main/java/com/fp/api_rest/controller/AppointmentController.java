package com.fp.api_rest.controller;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService _appointmentService;

    @GetMapping("/getAll")
    public List<Appointment> getAllAppointments() {
        return _appointmentService.findAll();
    }

    @PostMapping( "/create")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return _appointmentService.save(appointment);
    }
}
