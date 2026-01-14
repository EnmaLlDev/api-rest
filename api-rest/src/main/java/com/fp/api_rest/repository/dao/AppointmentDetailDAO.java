package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.AppointmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentDetailDAO extends JpaRepository<AppointmentDetail, Integer> {
    Optional<AppointmentDetail> findByAppointmentId(Integer appointmentId);
}

