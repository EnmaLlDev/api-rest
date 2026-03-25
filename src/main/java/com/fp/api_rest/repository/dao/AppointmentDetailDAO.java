package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.AppointmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentDetailDAO extends JpaRepository<AppointmentDetail, Integer> {
    @Query("SELECT ad FROM AppointmentDetail ad WHERE ad.appointment.id = :appointmentId")
    List<AppointmentDetail> findByAppointmentId(@Param("appointmentId") Integer appointmentId);
}
