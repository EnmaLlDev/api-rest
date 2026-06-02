package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.PatientDTO;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de utilidad para mapear entre Patient y PatientDTO.
 */
public class PatientMapper {
    /**
     * Convierte una entidad Patient a PatientDTO.
     * @param pacient entidad a convertir
     * @return DTO equivalente
     */
    public static PatientDTO toDTO(Patient pacient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(pacient.getId());
        dto.setDni(pacient.getDni());
        dto.setFirstName(pacient.getFirstName());
        dto.setSecondName(pacient.getSecondName());
        dto.setLastName(pacient.getLastName());
        dto.setSecondLastName(pacient.getSecondLastName());
        dto.setEmail(pacient.getEmail());
        dto.setPhone(pacient.getPhone());
        dto.setBirthDate(pacient.getBirthDate());
        dto.setAddress(pacient.getAddress());
        List<Doctor> docs = pacient.getDoctors();
        dto.setDoctorIds(docs != null
                ? docs.stream().map(Doctor::getId).collect(Collectors.toList())
                : Collections.emptyList());

        return dto;
    }

    /**
     * Convierte un PatientDTO a entidad Patient.
     * @param dto DTO a convertir
     * @return entidad equivalente
     */
    public static Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setDni(dto.getDni());
        patient.setFirstName(dto.getFirstName());
        patient.setSecondName(dto.getSecondName());
        patient.setLastName(dto.getLastName());
        patient.setSecondLastName(dto.getSecondLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        patient.setBirthDate(dto.getBirthDate());
        patient.setAddress(dto.getAddress());

        return  patient;
    }
}
