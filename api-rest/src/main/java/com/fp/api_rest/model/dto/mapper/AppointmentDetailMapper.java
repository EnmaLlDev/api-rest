package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.AppointmentDetail;
import com.fp.api_rest.model.dto.AppointmentDetailDTO;

public class AppointmentDetailMapper {

    public static AppointmentDetailDTO toDTO(AppointmentDetail detail) {
        if (detail == null) {
            return null;
        }

        AppointmentDetailDTO dto = new AppointmentDetailDTO();
        dto.setId(detail.getId());
        dto.setAppointmentId(detail.getAppointment() != null ? detail.getAppointment().getId() : null);
        dto.setDiagnosis(detail.getDiagnosis());
        dto.setPrescription(detail.getPrescription());
        dto.setNotes(detail.getNotes());
        dto.setTreatment(detail.getTreatment());
        dto.setFollowUp(detail.getFollowUp());
        return dto;
    }

    public static AppointmentDetail toEntity(AppointmentDetailDTO dto) {
        if (dto == null) {
            return null;
        }

        AppointmentDetail detail = new AppointmentDetail();
        detail.setDiagnosis(dto.getDiagnosis());
        detail.setPrescription(dto.getPrescription());
        detail.setNotes(dto.getNotes());
        detail.setTreatment(dto.getTreatment());
        detail.setFollowUp(dto.getFollowUp());

        // Asignar el appointment si viene el ID
        if (dto.getAppointmentId() != null) {
            Appointment appointment = new Appointment();
            appointment.setId(dto.getAppointmentId());
            detail.setAppointment(appointment);
        }

        return detail;
    }
}

