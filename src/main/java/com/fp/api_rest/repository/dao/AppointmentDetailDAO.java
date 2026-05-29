package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.AppointmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio JPA para la entidad AppointmentDetail (detalles de citas médicas).
 */
public interface AppointmentDetailDAO extends JpaRepository<AppointmentDetail, Integer> {
    /**
     * Obtiene los detalles clínicos de una cita específica.
     * @param appointmentId identificador de la cita
     * @return lista de detalles asociados
     */
    @Query("SELECT ad FROM AppointmentDetail ad WHERE ad.appointment.id = :appointmentId")
    List<AppointmentDetail> findByAppointmentId(@Param("appointmentId") Integer appointmentId);

    /**
     * Obtiene los detalles clínicos de un paciente específico.
     * @param patientId identificador del paciente
     * @return lista de detalles del paciente
     */
    @Query("SELECT ad FROM AppointmentDetail ad WHERE ad.appointment.patient.id = :patientId")
    List<AppointmentDetail> findByPatientId(@Param("patientId") Integer patientId);
}
