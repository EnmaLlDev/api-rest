package com.fp.api_rest.service;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dao.DoctorDAOnew;
import com.fp.api_rest.repository.DoctorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private DoctorDAOnew doctorDAOnew;
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public String getAllNames() {
        List<Doctor> doctors = doctorRepository.findAll();
        StringBuilder names = new StringBuilder();
        for (Doctor doctor : doctors) {
            String first = doctor.getFirst_name();
            String second = doctor.getLast_name();
            if (first != null && !first.isBlank()) {
                if (names.length() > 0) names.append(" ");
                names.append(first);
            }
            if (second != null && !second.isBlank()) {
                if (names.length() > 0) names.append(" ");
                names.append(second);
            }

        }
        return names.toString();
    }

    @Transactional(readOnly = true)
    public List<String> getAllFirstNames() {
        return doctorRepository.findAll().stream()
                .map(Doctor::getFirst_name)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public Doctor updateDoctor(Doctor doctor) {
        if (doctorRepository.existsById(doctor.getId())) {
            return doctorRepository.save(doctor);
        } else {
            throw new IllegalArgumentException("Doctor with id " + doctor.getId() + " does not exist.");
        }
    }
}