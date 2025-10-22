package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
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
    public <S extends Patient> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Patient> findAll() {
        TypedQuery<Patient> query = entityManager.createQuery("SELECT p FROM Patient p", Patient.class);
        return query.getResultList();
    }

    @Override
    public List<Patient> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
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
    public void delete(Patient entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Patient> entities) {

    }

    @Override
    public void deleteAll() {

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
            "SELECT p FROM Patient p WHERE p.birth_date BETWEEN :inicio AND :fin", Patient.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fin", fin);
        return query.getResultList();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Patient> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Patient> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Patient> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Patient getOne(Integer integer) {
        return null;
    }

    @Override
    public Patient getById(Integer integer) {
        return null;
    }

    @Override
    public Patient getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Patient> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Patient> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Patient> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Patient> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Patient> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Patient> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Patient, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Patient> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Patient> findAll(Pageable pageable) {
        return null;
    }
}
