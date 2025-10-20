package com.fp.api_rest.model.dto.converter;

import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.PatientDTO;

public class PatientConverter {
    public static PatientDTO toDTO(Patient patient) {
        if (patient == null) return null;

        return PatientDTO.builder()
                .id(patient.getId())
                .dni(patient.getDni())
                .first_name(patient.getFirst_name())
                .second_name(patient.getSecond_name())
                .last_name(patient.getLast_name())
                .second_last_name(patient.getSecond_last_name())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .birth_date(patient.getBirth_date())
                .address(patient.getAddress())
                .build();
    }

    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;

        return Patient.builder()
                .id(dto.getId())
                .dni(dto.getDni())
                .first_name(dto.getFirst_name())
                .second_name(dto.getSecond_name())
                .last_name(dto.getLast_name())
                .second_last_name(dto.getSecond_last_name())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .birth_date(dto.getBirth_date())
                .address(dto.getAddress())
                .build();
    }
}
