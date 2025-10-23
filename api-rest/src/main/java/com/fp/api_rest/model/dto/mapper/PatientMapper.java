package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.PatientDTO;

public class PatientMapper {
    public static PatientDTO toDTO(Patient pacient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(pacient.getId());
        dto.setFirstName(pacient.getFirstName());
        dto.setSecondName(pacient.getSecondName());
        dto.setLastName(pacient.getLastName());
        dto.setSecondLastName(pacient.getSecondLastName());
        dto.setEmail(pacient.getEmail());
        dto.setPhone(pacient.getPhone());
        dto.setBirthDate(pacient.getBirthDate());
        dto.setAddress(pacient.getAddress());

        return dto;
    }

    public static Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
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
