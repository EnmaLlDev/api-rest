package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.StateTreatment;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TreatmentDAO implements BaseDAO<Treatment, Integer> {
    private final EntityManager entityManager;

    public TreatmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Treatment> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Treatment.class, id));
    }

    @Override
    public <S extends Treatment> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Treatment> findAll() {
        TypedQuery<Treatment> query = entityManager.createQuery("SELECT t FROM Treatment t", Treatment.class);
        return query.getResultList();
    }

    @Override
    public List<Treatment> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }


    //TODO revisar QUERY
    // Id NO DEBIRIA SER INT SINO

    @Override
    public Treatment save(Treatment treatment) {
        for (Treatment existingTreatment : findAll()) {
            if (existingTreatment.getId() == treatment.getId()) {
                throw new IllegalArgumentException("Ya existe un tratamiento con el ID: " + treatment.getId());
            }
        }
        return entityManager.merge(treatment);
    }


@Override
public void deleteById(Integer id) {
    findById(id).ifPresent(entityManager::remove);
}

    @Override
    public void delete(Treatment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Treatment> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
public boolean existsById(Integer id) {
    return findById(id).isPresent();
}

public List<Treatment> findByPacienteId(int pacienteId) {
    TypedQuery<Treatment> query = entityManager.createQuery(
            "SELECT t FROM Treatment t WHERE t.patient_id = :pacienteId", Treatment.class);
    query.setParameter("pacienteId", pacienteId);
    return query.getResultList();
}

public List<Treatment> findByEstado(StateTreatment status) {
    TypedQuery<Treatment> query = entityManager.createQuery(
            "SELECT t FROM Treatment t WHERE t.status = :status", Treatment.class);
    query.setParameter("status", status);
    return query.getResultList();
}
    @Override
    public void flush() {

    }

    @Override
    public <S extends Treatment> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Treatment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Treatment> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Treatment getOne(Integer integer) {
        return null;
    }

    @Override
    public Treatment getById(Integer integer) {
        return null;
    }

    @Override
    public Treatment getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Treatment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Treatment> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Treatment> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Treatment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Treatment> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Treatment> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Treatment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Treatment> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Treatment> findAll(Pageable pageable) {
        return null;
    }
}
