package com.fp.api_rest.model.dto.converter;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.PatientDTO;

public class PatientConverter {
    public static PatientDTO toDTO(Patient patient) {
        if (patient == null) return null;

        return PatientDTO.builder()
                .id(patient.getId())
                .dni(patient.getDni())
                .first_name(patient.getFirstName())
                .second_name(patient.getSecondName())
                .last_name(patient.getLastName())
                .second_last_name(patient.getSecondLastName())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .birth_date(patient.getBirthDate())
                .address(patient.getAddress())
                .build();
    }

    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;

        return Patient.builder()
                .id(dto.getId())
                .dni(dto.getDni())
                .firstName(dto.getFirst_name())
                .secondLastName(dto.getSecond_name())
                .lastName(dto.getLast_name())
                .secondLastName(dto.getSecond_last_name())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .birthDate(dto.getBirth_date())
                .address(dto.getAddress())
                .build();
    }
}
