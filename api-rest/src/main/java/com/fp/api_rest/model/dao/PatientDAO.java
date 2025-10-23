package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.time.LocalDate;
import java.util.List;

public interface PatientDAO extends BaseDAO<Patient, Integer> {
    List<Patient> findByBirthDateGreaterThan(LocalDate date);
    List<Patient> findByAddress(String address);
}
