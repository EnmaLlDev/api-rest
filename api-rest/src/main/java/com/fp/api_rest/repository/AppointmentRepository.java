package com.fp.api_rest.repository;

import com.fp.api_rest.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {



}
