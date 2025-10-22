package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.StateAppointment;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
    public <S extends Appointment> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Appointment> findAll() {
        TypedQuery<Appointment> query = entityManager.createQuery("SELECT a FROM Appointment a", Appointment.class);
        return query.getResultList();
    }

    @Override
    public List<Appointment> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
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
    public void delete(Appointment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Appointment> entities) {

    }

    @Override
    public void deleteAll() {

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
    public void flush() {

    }

    @Override
    public <S extends Appointment> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Appointment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Appointment> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Appointment getOne(Integer integer) {
        return null;
    }

    @Override
    public Appointment getById(Integer integer) {
        return null;
    }

    @Override
    public Appointment getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Appointment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Appointment> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Appointment> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Appointment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Appointment> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Appointment> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Appointment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Appointment> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Appointment> findAll(Pageable pageable) {
        return null;
    }
}