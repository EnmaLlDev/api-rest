package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientDAO extends JpaRepository<Patient, Integer> {
    List<Patient> findByAddressContaining(String address);
    void deleteAll();
}
