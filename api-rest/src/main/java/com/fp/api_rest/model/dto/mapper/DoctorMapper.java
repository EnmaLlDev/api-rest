package com.fp.api_rest.model.dto.mapper;


import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.model.Doctor;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirst_name());
        dto.setLastName(doctor.getLast_name());
        dto.setDni(doctor.getDni());
        dto.setEmail(doctor.getEmail());
        dto.setPhone(doctor.getPhone());
        dto.setLicenseNumber(doctor.getLicenseNumber());
        dto.setSpecialty(doctor.getSpecialty());
        return dto;
    }

    public static Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setFirst_name(dto.getFirstName());
        doctor.setSecond_last_name(dto.getLastName());
        doctor.setDni(dto.getDni());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setSpecialty(dto.getSpecialty());
        return doctor;
    }
}