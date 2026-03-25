package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findBySpecialtyContainingIgnoreCase(String keyword);
}
