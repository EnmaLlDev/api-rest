package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dto.TreatmentDTO;

public class TreatmentMapper {
    public static TreatmentDTO toDTO(Treatment treatment) {
        TreatmentDTO dto = new TreatmentDTO();
        dto.setId(treatment.getId());
        dto.setDescription(treatment.getDescription());
        dto.setCost(treatment.getCost());
        dto.setStart_date(treatment.getStart_date());
        dto.setEnd_date(treatment.getEnd_date());
        dto.setStatus(treatment.getStatus());
        dto.setPatientId(treatment.getPatientId() != null ?
                treatment.getPatientId().getId() : 0);
        dto.setDoctorId(treatment.getDoctorId() != null ?
                treatment.getDoctorId().getId() : 0);
        return dto;

    }

    public static Treatment toEntity(TreatmentDTO dto) {
        Treatment treatment = new Treatment();
        treatment.setId(dto.getId());
        treatment.setDescription(dto.getDescription());
        treatment.setCost(dto.getCost());
        treatment.setStart_date(dto.getStart_date());
        treatment.setEnd_date(dto.getEnd_date());
        treatment.setStatus(dto.getStatus());

        // fetch Patient and Doctor by their IDs
        Patient patient = new Patient();
        patient.setId(dto.getPatientId());
        treatment.setPatientId(patient);
        Doctor doctor = new Doctor();
        doctor.setId(dto.getDoctorId());
        treatment.setDoctorId(doctor);
        return treatment;
    }
}
