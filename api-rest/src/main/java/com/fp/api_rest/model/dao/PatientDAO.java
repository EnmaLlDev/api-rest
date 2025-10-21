package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PatientDAO implements BaseDAO<Patient, Integer> {
    private final EntityManager entityManager;

    public PatientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Patient> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Patient.class, id));
    }

    @Override
    public List<Patient> findAll() {
        TypedQuery<Patient> query = entityManager.createQuery("SELECT p FROM Patient p", Patient.class);
        return query.getResultList();
    }

    @Override
    public Patient save(Patient patient) {
        if (patient.getId() == 0) {
            entityManager.persist(patient);
            return patient;
        } else {
            return entityManager.merge(patient);
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

    public Optional<Patient> findByDni(String dni) {
        TypedQuery<Patient> query = entityManager.createQuery(
            "SELECT p FROM Patient p WHERE p.dni = :dni", Patient.class);
        query.setParameter("dni", dni);
        List<Patient> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Patient> findByFechaNacimientoBetween(LocalDate inicio, LocalDate fin) {
        TypedQuery<Patient> query = entityManager.createQuery(
            "SELECT p FROM Patient p WHERE p.fechaNacimiento BETWEEN :inicio AND :fin", Patient.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fin", fin);
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
