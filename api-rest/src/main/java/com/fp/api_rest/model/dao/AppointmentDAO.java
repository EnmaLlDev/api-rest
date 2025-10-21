package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.StateAppointment;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AppointmentDAO implements BaseDAO<Appointment, Integer> {
    private final EntityManager entityManager;

    public AppointmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Appointment.class, id));
    }

    @Override
    public List<Appointment> findAll() {
        TypedQuery<Appointment> query = entityManager.createQuery("SELECT a FROM Appointment a", Appointment.class);
        return query.getResultList();
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getId() == 0) {
            entityManager.persist(appointment);
            return appointment;
        } else {
            return entityManager.merge(appointment);
        }
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    public List<Appointment> findByPacienteId(int patientId) {
        TypedQuery<Appointment> query = entityManager.createQuery(
            "SELECT a FROM Appointment a WHERE a.patient.id = :pacienteId ORDER BY a.fechaHora DESC",
            Appointment.class);
        query.setParameter("pacienteId", patientId);
        return query.getResultList();
    }

    public List<Appointment> findByMedicoId(int doctorId) {
        TypedQuery<Appointment> query = entityManager.createQuery(
            "SELECT a FROM Appointment a WHERE a.doctor.id = :medicoId ORDER BY a.fechaHora DESC",
            Appointment.class);
        query.setParameter("medicoId", doctorId);
        return query.getResultList();
    }

    public List<Appointment> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin) {
        TypedQuery<Appointment> query = entityManager.createQuery(
            "SELECT a FROM Appointment a WHERE a.fechaHora BETWEEN :inicio AND :fin ORDER BY a.fechaHora",
            Appointment.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fin", fin);
        return query.getResultList();
    }

    public List<Appointment> findByEstado(StateAppointment state) {
        TypedQuery<Appointment> query = entityManager.createQuery(
            "SELECT a FROM Appointment a WHERE a.status = :estado ORDER BY a.fechaHora",
            Appointment.class);
        query.setParameter("estado", state);
        return query.getResultList();
    }

    @Override
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void commit() {
        entityManager.getTransaction().commit();
    }

    @Override
    public void rollback() {
        entityManager.getTransaction().rollback();
    }
}