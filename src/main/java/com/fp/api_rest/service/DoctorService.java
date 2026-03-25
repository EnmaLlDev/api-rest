package com.fp.api_rest.service;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.repository.dao.DoctorDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.model.dto.mapper.DoctorMapper;

@Service
public class DoctorService {

    @Autowired
    private final DoctorDAO doctorDAO;

    public DoctorService(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    public List<DoctorDTO> findAll() {
        return doctorDAO.findAll()
                .stream()
                .map(DoctorMapper::toDTO)
                .toList();
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

    public Doctor update(Integer id, DoctorDTO data) {
        Doctor existing = doctorDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + id));

        // Actualización parcial: solo actualizar campos no vacíos
        if (data.getDni() != null && !data.getDni().isBlank()) {
            existing.setDni(data.getDni());
        }
        if (data.getFirstName() != null && !data.getFirstName().isBlank()) {
            existing.setFirstName(data.getFirstName());
        }
        if (data.getSecondName() != null && !data.getSecondName().isBlank()) {
            existing.setSecondName(data.getSecondName());
        }
        if (data.getLastName() != null && !data.getLastName().isBlank()) {
            existing.setLastName(data.getLastName());
        }
        if (data.getSecondLastName() != null && !data.getSecondLastName().isBlank()) {
            existing.setSecondLastName(data.getSecondLastName());
        }
        if (data.getEmail() != null && !data.getEmail().isBlank()) {
            existing.setEmail(data.getEmail());
        }
        if (data.getPhone() != null && !data.getPhone().isBlank()) {
            existing.setPhone(data.getPhone());
        }
        if (data.getSpecialty() != null && !data.getSpecialty().isBlank()) {
            existing.setSpecialty(data.getSpecialty());
        }
        if (data.getLicenseNumber() != null && !data.getLicenseNumber().isBlank()) {
            existing.setLicenseNumber(data.getLicenseNumber());
        }
        return doctorDAO.save(existing);
    }
    public void deleteById(Integer id) {
        doctorDAO.deleteById(id);
    }
    

    public List<DoctorDTO> findByLicenseNumber (String licenseNumber) {
        return doctorDAO.findByLicenseNumber(licenseNumber)
                .stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }

    public List<DoctorDTO> findBySpecialtyContainingIgnoreCase(String specialty) {
        return doctorDAO.findBySpecialtyContainingIgnoreCase(specialty)
                .stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }
}