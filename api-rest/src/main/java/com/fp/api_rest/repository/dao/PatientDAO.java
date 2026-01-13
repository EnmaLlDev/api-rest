package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.base.BaseDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends JpaRepository<Patient, Integer> {
    List<Patient> findByAddressContaining(String address);
    void deleteAll();
}
