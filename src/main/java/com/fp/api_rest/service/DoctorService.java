package com.fp.api_rest.service;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.repository.dao.DoctorDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.model.dto.mapper.DoctorMapper;

/**
 * Servicio para la gestión de médicos.
 */
@Service
public class DoctorService {

    @Autowired
    private final DoctorDAO doctorDAO;

    public DoctorService(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    /**
     * Lista todos los médicos.
     * @return lista de médicos como DTO
     */
    public List<DoctorDTO> findAll() {
        return doctorDAO.findAll()
                .stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }

    /**
     * Busca un médico por su ID.
     * @param id identificador del médico
     * @return DTO del médico o null si no existe
     */
    public DoctorDTO findById(Integer id) {
        return doctorDAO.findById(id)
                .map(DoctorMapper::toDTO)
                .orElse(null);
    }

    /**
     * Crea un nuevo médico.
     * @param dto datos del médico
     * @return DTO del médico creado
     */
    public DoctorDTO save(DoctorDTO dto) {
        Doctor doctor = DoctorMapper.toEntity(dto);
        Doctor saved = doctorDAO.save(doctor);
        return DoctorMapper.toDTO(saved);
    }

    /**
     * Actualiza un médico existente de forma parcial.
     * @param id identificador del médico
     * @param data datos a actualizar
     * @return médico actualizado
     */
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
    /**
     * Elimina un médico por su ID.
     * @param id identificador del médico
     */
    public void deleteById(Integer id) {
        doctorDAO.deleteById(id);
    }
    

    /**
     * Busca médicos por número de licencia.
     * @param licenseNumber número de licencia
     * @return lista de médicos coincidentes
     */
    public List<DoctorDTO> findByLicenseNumber (String licenseNumber) {
        return doctorDAO.findByLicenseNumber(licenseNumber)
                .stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }

    /**
     * Busca médicos por especialidad ignorando mayúsculas.
     * @param specialty especialidad a buscar
     * @return lista de médicos coincidentes
     */
    public List<DoctorDTO> findBySpecialtyContainingIgnoreCase(String specialty) {
        return doctorDAO.findBySpecialtyContainingIgnoreCase(specialty)
                .stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }
}