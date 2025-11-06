package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.base.BaseDAO;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends BaseDAO<Patient, Integer> {

    Optional<Patient> findById(Long id);
    List<Patient> findByAddressContaining(String address);
}
