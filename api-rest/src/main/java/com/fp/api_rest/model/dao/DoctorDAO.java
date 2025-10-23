package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO extends BaseDAO<Doctor, Integer> {
    List<Doctor> findBySpecialty(String specialty);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findBySpecialtyContainingIgnoreCase(String keyword);
}
