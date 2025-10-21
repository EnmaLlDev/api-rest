package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class DoctorDAO implements BaseDAO<Doctor, Integer> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> findById(Integer integer) {
        Doctor doctor = entityManager.find(Doctor.class, integer);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return entityManager.createQuery("SELECT d FROM Doctor d", Doctor.class)
                .getResultList();
    }

    @Override
    public Doctor save(Doctor doctor) {
        for( Doctor existingDoctor : findAll()) {
            if (existingDoctor.getId()  == doctor.getId()) {
                throw new IllegalArgumentException("Ya existe un doctor con el DNI: " + doctor.getDni());
            }
        }
        return entityManager.merge(doctor);
    }

    @Override
    public void deleteById(Integer id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        if (doctor != null) {
            entityManager.remove(doctor);
        } else {
            throw new IllegalArgumentException("Doctor with id " + id + " does not exist.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return entityManager.find(Doctor.class, id) != null;
    }
}
