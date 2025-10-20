package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DoctorDAO implements BaseDAO<Doctor, Integer> {

    @jakarta.persistence.PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Doctor> findById(Integer integer) {
        Doctor doctor = entityManager.find(Doctor.class, integer);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    @Override
    public List<Doctor> findAll() {
        return entityManager.createQuery("SELECT d FROM Doctor d", Doctor.class)
                .getResultList();
    }

    @Override
    public Doctor save(Doctor entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void deleteById(Integer integer) {
        if(existsById(integer)) {
            entityManager.createQuery("DELETE FROM Doctor d WHERE d.id = :id");
        } else {
            throw new IllegalArgumentException("Doctor with id " + integer + " does not exist.");
        }
    }

    @Override
    public boolean existsById(Integer integer) {
        if(entityManager.find(Doctor.class, integer) != null ) {
            return true;
        }
        return false;
    }
}
