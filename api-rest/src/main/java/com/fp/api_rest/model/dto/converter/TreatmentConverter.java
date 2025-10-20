package com.fp.api_rest.model.dto.converter;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dto.TreatmentDTO;

public class TreatmentConverter {
    public static TreatmentDTO toDTO(Treatment treatment) {
        if (treatment == null) return null;

        return TreatmentDTO.builder()
                .id(treatment.getId())
                .description(treatment.getDescription())
                .cost(treatment.getCost())
                .start_date(treatment.getStart_date())
                .end_date(treatment.getEnd_date())
                .patient_id(treatment.getPatient_id().getId())
                .status(treatment.getStatus())
                .build();
    }

    public static Treatment toEntity(TreatmentDTO dto) {
        if (dto == null) return null;

        return Treatment.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .cost(dto.getCost())
                .start_date(dto.getStart_date())
                .end_date(dto.getEnd_date())
                .status(dto.getStatus())
                .patient_id(dto.getPatient_id() != 0 ?
                        Patient.builder().id(dto.getPatient_id()).build() : null)
                .doctor_id( dto.getDoctor_id() != 0 ?
                        Doctor.builder().id(dto.getDoctor_id()).build() : null)
                .build();
    }
}
