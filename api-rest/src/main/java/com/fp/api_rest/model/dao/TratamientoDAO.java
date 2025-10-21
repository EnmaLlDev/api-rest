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

    @Override
    public Treatment save(Treatment treatment) {
        if (treatment.getId() == 0) {
            entityManager.persist(treatment);
            return treatment;
        } else {
            return entityManager.merge(treatment);
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

    public List<Treatment> findByPacienteId(int pacienteId) {
        TypedQuery<Treatment> query = entityManager.createQuery(
            "SELECT t FROM Treatment t WHERE t.patient.id = :pacienteId", Treatment.class);
        query.setParameter("pacienteId", pacienteId);
        return query.getResultList();
    }

    public List<Treatment> findByEstado(StateTreatment estado) {
        TypedQuery<Treatment> query = entityManager.createQuery(
            "SELECT t FROM Treatment t WHERE t.estado = :estado", Treatment.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }
}
