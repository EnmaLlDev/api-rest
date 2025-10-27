package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Diagnostic;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dto.DiagnosticDTO;
import com.fp.api_rest.model.dto.TreatmentDTO;

public class DiagnosticMapper {

    public static DiagnosticDTO toDTO (Diagnostic diagnostic) {
    DiagnosticDTO dto = new DiagnosticDTO();
        dto.setId(diagnostic.getId());
        dto.setDescription(diagnostic.getDescription());
        dto.setDate(diagnostic.getDate());
        dto.setPatientDTO(diagnostic.getPatientId() != null ?
                PatientMapper.toDTO(diagnostic.getPatientId()) : null);
        dto.setDoctorDTO(diagnostic.getDoctorId() != null ?
                DoctorMapper.toDTO(diagnostic.getDoctorId()) : null);

        return dto;
    }

    public static Diagnostic toEntity(DiagnosticDTO dto) {
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setId(dto.getId());
        diagnostic.setDescription(dto.getDescription());
        diagnostic.setDate(dto.getDate());
        Patient patient = new Patient();
            patient.setId(dto.getPatientDTO().getId());
            diagnostic.setPatientId(patient);
        Doctor doctor = new Doctor();
            doctor.setId(dto.getDoctorDTO().getId());
            diagnostic.setDoctorId(doctor);
        return diagnostic;
    }
}
