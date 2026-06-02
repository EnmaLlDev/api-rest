package com.fp.api_rest.model.dto.mapper;


import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.dto.DoctorDTO;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de utilidad para mapear entre Doctor y DoctorDTO.
 */
public class DoctorMapper {

    /**
     * Convierte una entidad Doctor a DoctorDTO.
     * @param doctor entidad a convertir
     * @return DTO equivalente
     */
    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setSecondName(doctor.getSecondName());
        dto.setLastName(doctor.getLastName());
        dto.setSecondLastName(doctor.getSecondLastName());
        dto.setDni(doctor.getDni());
        dto.setEmail(doctor.getEmail());
        dto.setPhone(doctor.getPhone());
        dto.setLicenseNumber(doctor.getLicenseNumber());
        dto.setSpecialty(doctor.getSpecialty());
        List<Patient> pats = doctor.getPatients();
        dto.setPatientIds(pats != null
                ? pats.stream().map(Patient::getId).collect(Collectors.toList())
                : Collections.emptyList());
        return dto;
    }

    /**
     * Convierte un DoctorDTO a entidad Doctor.
     * @param dto DTO a convertir
     * @return entidad equivalente
     */
    public static Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setFirstName(dto.getFirstName());
        doctor.setSecondName(dto.getSecondName());
        doctor.setLastName(dto.getLastName());
        doctor.setSecondLastName(dto.getSecondLastName());
        doctor.setDni(dto.getDni());
        doctor.setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setSpecialty(dto.getSpecialty());
        return doctor;
    }
}