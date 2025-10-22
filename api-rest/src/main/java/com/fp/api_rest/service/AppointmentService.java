package com.fp.api_rest.service;

import com.fp.api_rest.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        return appointments;
    }
    public Appointment save(Appointment appointment) {
        return appointment;
    }

    public void deleteById(int id) {
    }
}