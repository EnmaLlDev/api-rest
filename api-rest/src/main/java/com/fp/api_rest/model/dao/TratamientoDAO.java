package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.StateTreatment;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class TratamientoDAO implements BaseDAO<Treatment, Integer> {
    private final EntityManager entityManager;

    public TratamientoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Treatment> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Treatment.class, id));
    }

    @Override
    public List<Treatment> findAll() {
        TypedQuery<Treatment> query = entityManager.createQuery("SELECT t FROM Treatment t", Treatment.class);
        return query.getResultList();
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
