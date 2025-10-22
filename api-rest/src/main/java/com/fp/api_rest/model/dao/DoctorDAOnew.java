package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.util.List;

public interface DoctorDAOnew extends BaseDAO<Doctor, Integer> {
    public List<Doctor> getAll();
    public Doctor getDoctor();
    public String getNumberLicense(Doctor doctor);
    public String getSpecialty(Doctor doctor);
}
