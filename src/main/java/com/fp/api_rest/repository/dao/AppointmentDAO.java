package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.StateAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Appointment (citas médicas).
 */
public interface AppointmentDAO extends JpaRepository<Appointment, Integer> {
    /**
     * Busca citas por motivo de consulta.
     * @param reason motivo de la cita
     * @return lista de citas coincidentes
     */
    List<Appointment> findByReason(String reason);
    /**
     * Busca citas por estado.
     * @param status estado de la cita
     * @return lista de citas en ese estado
     */
    List<Appointment> findByStatus(StateAppointment status);
    /**
     * Busca citas asociadas a un paciente.
     * @param patientId identificador del paciente
     * @return lista de citas del paciente
     */
    List<Appointment> findByPatientId(Integer patientId);
}