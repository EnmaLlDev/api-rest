package com.fp.api_rest.model.dto.converter;


import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dto.DoctorDTO;

public class DoctorConverter {
    public static DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) return null;

        return DoctorDTO.builder()
                .id(doctor.getId())
                .dni(doctor.getDni())
                .first_name(doctor.getFirst_name())
                .second_name(doctor.getSecond_name())
                .last_name(doctor.getLast_name())
                .second_last_name(doctor.getSecond_last_name())
                .email(doctor.getEmail())
                .phone(doctor.getPhone())
                .license_number(doctor.getLicense_number())
                .specialty(doctor.getSpecialty())
                .build();
    }

    public static Doctor toEntity(DoctorDTO dto) {
        if (dto == null) return null;

        return Doctor.builder()
                .id(dto.getId())
                .dni(dto.getDni())
                .first_name(dto.getFirst_name())
                .second_name(dto.getSecond_name())
                .last_name(dto.getLast_name())
                .second_last_name(dto.getSecond_last_name())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .license_number(dto.getLicense_number())
                .specialty(dto.getSpecialty())
                .build();
    }
}
