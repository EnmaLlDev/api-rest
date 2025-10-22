package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.base.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/*
@Repository
@Transactional
public class DoctorDAO implements BaseDAO<Doctor, Integer> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Doctor> findById(Integer integer) {
        return Optional.ofNullable(entityManager.find(Doctor.class, integer));
    }

    @Override
    public List<Doctor> findAll() {
        return entityManager.createQuery("FROM Doctor", Doctor.class).getResultList();
    }


    public void deleteById(Integer integer) {
        Doctor doctor = entityManager.find(Doctor.class, integer);
        if (doctor != null) {
            entityManager.remove(doctor);
        }
    }
}*/