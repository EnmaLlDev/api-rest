package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.base.BaseDAO;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO extends BaseDAO<Doctor, Integer> {

    Optional<Doctor> findById(Long id);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findBySpecialtyContainingIgnoreCase(String keyword);
}
