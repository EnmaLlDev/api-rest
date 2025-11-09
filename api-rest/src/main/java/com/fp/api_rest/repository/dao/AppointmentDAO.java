package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.StateAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentDAO extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByReason(String reason);
    List<Appointment> findByStatus(StateAppointment status);
}