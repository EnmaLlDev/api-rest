package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;

public interface TreatmentDAOnew extends BaseDAO<Treatment, Integer> {
    List<Treatment> findByStatus(String status);
    List<Treatment> findByPatientId(int patientId);
    List<Treatment> findByDoctorId(int doctorId);
    List<Double> findByCostGreaterThan(double cost);
}
