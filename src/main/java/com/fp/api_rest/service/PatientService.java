package com.fp.api_rest.service;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.model.dto.mapper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de pacientes.
 */
@Service
public class PatientService {

    @Autowired
    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    /**
     * Lista todos los pacientes.
     * @return lista de pacientes como DTO
     */
    public List<PatientDTO> getAllPatients() {
        return patientDAO.findAll()
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    /**
     * Busca un paciente por su ID.
     * @param id identificador del paciente
     * @return DTO del paciente o null si no existe
     */
    public PatientDTO findById(Integer id) {
        return patientDAO.findById(id)
                .map(PatientMapper::toDTO)
                .orElse(null);
    }

    /**
     * Crea un nuevo paciente.
     * @param patientDTO datos del paciente
     * @return DTO del paciente creado
     */
    public PatientDTO save(PatientDTO patientDTO) {
        Patient patient = PatientMapper.toEntity(patientDTO);
        Patient saved = patientDAO.save(patient);
        return PatientMapper.toDTO(saved);
    }

    /**
     * Actualiza un paciente existente de forma parcial.
     * @param id identificador del paciente
     * @param data datos a actualizar
     * @return DTO del paciente actualizado
     */
    public PatientDTO update(Integer id, PatientDTO data) {
        Patient existing = patientDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id " + id));

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
        if (data.getBirthDate() != null) {
            existing.setBirthDate(data.getBirthDate());
        }
        if (data.getAddress() != null && !data.getAddress().isBlank()) {
            existing.setAddress(data.getAddress());
        }
        return PatientMapper.toDTO(patientDAO.save(existing));
    }

    /**
     * Elimina un paciente por su ID.
     * @param id identificador del paciente
     */
    public void deletePatient(Integer id) {
        patientDAO.deleteById(id);
    }

    /**
     * Busca pacientes por dirección.
     * @param address dirección a buscar
     * @return lista de pacientes coincidentes
     */
    public List<PatientDTO> findByAddressContaining(String address ) {
        return patientDAO.findByAddressContaining( address)
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    /**
     * Busca un paciente por su email.
     * @param email email del paciente
     * @return DTO del paciente o null si no existe
     */
    public PatientDTO findByEmail(String email) {
        return patientDAO.findByEmail(email)
                .map(PatientMapper::toDTO)
                .orElse(null);
    }
}