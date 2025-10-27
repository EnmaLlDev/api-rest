package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.StateAppointment;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;

public interface AppointmentDAO extends BaseDAO<Appointment, Integer> {
    List<Appointment> findByReason(String reason);
    List<Appointment> findByStatus(StateAppointment status);
}