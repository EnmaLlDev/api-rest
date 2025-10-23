package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;

public interface TreatmentDAOnew extends BaseDAO<Treatment, Integer> {
    List<Treatment> findByStatus(String status);
    List<Double> findByCostGreaterThan(double cost);
}