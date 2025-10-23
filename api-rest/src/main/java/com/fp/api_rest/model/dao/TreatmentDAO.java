package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.StateTreatment;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;

public interface TreatmentDAO extends BaseDAO<Treatment, Integer> {
    List<Treatment> findByStatus(StateTreatment status);
    List<Treatment> findByCostGreaterThan(double cost);
}