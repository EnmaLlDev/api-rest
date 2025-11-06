package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.StateAppointment;
import com.fp.api_rest.repository.dao.base.BaseDAO;

import java.util.List;
import java.util.Optional;

public interface AppointmentDAO extends BaseDAO<Appointment, Integer> {

    Optional<Appointment> findById(Long id);
    List<Appointment> findByReason(String reason);
    List<Appointment> findByStatus(StateAppointment status);
}