package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Diagnostic;
import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dto.DiagnosticDTO;
import com.fp.api_rest.model.dto.TreatmentDTO;

public class DiagnosticMapper {

    public static Diagnostic toDTO (Diagnostic diagnostic) {
    DiagnosticDTO dto = new DiagnosticDTO();
        dto.setId(diagnostic.getId());
        dto.setDescription(diagnostic.getDescription());
        dto.setDate(diagnostic.getDate());

        dto.setPatient(diagnostic.getPatientId() != null ?
                diagnostic.getPatientId() : null);
        dto.setDoctor(diagnostic.getDoctorId() != null ?
                diagnostic.getDoctorId() : null);
        return dto;
    }

    public static Diagnostic toEntity(DiagnosticDTO dto) {
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setId(dto.getId());
        diagnostic.setDescription(dto.getDescription());
        diagnostic.setDate(dto.getDate());
        // fetch Patient and Doctor by their IDs
        Patient patient = new Patient();
            patient.setId(dto.getPatient().getId());
            diagnostic.setPatientId(patient);
        Doctor doctor = new Doctor();
            doctor.setId(dto.getDoctor().getId());
            diagnostic.setDoctorId(doctor);
        return diagnostic;
    }
}
