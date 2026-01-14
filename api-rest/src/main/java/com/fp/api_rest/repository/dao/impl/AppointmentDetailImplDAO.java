package com.fp.api_rest.repository.dao.impl;

import com.fp.api_rest.model.AppointmentDetail;
import com.fp.api_rest.repository.dao.AppointmentDetailDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDetailImplDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private final AppointmentDetailDAO appointmentDetailDAO;

    public AppointmentDetailImplDAO(AppointmentDetailDAO appointmentDetailDAO) {
        this.appointmentDetailDAO = appointmentDetailDAO;
    }

    public Optional<AppointmentDetail> findById(Integer id) {
        return appointmentDetailDAO.findById(id);
    }

    public List<AppointmentDetail> findAll() {
        return appointmentDetailDAO.findAll();
    }

    public AppointmentDetail save(AppointmentDetail entity) {
        return appointmentDetailDAO.save(entity);
    }

    public List<AppointmentDetail> saveAll(List<AppointmentDetail> entities) {
        return appointmentDetailDAO.saveAll(entities);
    }

    public void deleteById(Integer id) {
        appointmentDetailDAO.deleteById(id);
    }

    public Optional<AppointmentDetail> findByAppointmentId(Integer appointmentId) {
        return appointmentDetailDAO.findByAppointmentId(appointmentId);
    }
}

