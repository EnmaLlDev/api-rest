package com.fp.api_rest.service;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.DoctorDAOnew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.model.dto.mapper.DoctorMapper;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private final DoctorDAOnew doctorDAO;

    public DoctorService(DoctorDAOnew doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    public List<DoctorDTO> findAll() {
        return doctorDAO.findAll()
                .stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO findById(Integer id) {
        return doctorDAO.findById(id)
                .map(DoctorMapper::toDTO)
                .orElse(null);
    }

    public DoctorDTO save(DoctorDTO dto) {
        Doctor doctor = DoctorMapper.toEntity(dto);
        Doctor saved = doctorDAO.save(doctor);
        return DoctorMapper.toDTO(saved);
    }

    public void deleteById(Integer id) {
        doctorDAO.deleteById(id);
    }

    public List<DoctorDTO> findBySpecialty(String specialty) {
        return doctorDAO.findBySpecialty(specialty)
                .stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<DoctorDTO> findBySpecialtyContainingIgnoreCase(String specialty) {
        return doctorDAO.findBySpecialtyContainingIgnoreCase(specialty)
                .stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<DoctorDTO> findByLicenseNumber (String licenseNumber) {
        return doctorDAO.findByLicenseNumber(licenseNumber)
                .stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

}