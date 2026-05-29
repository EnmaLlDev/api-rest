package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Appointment;
import com.fp.api_rest.model.AppointmentDetail;
import com.fp.api_rest.model.dto.DetailsDTO;

/**
 * Clase de utilidad para mapear entre AppointmentDetail y DetailsDTO.
 */
public class AppointmentDetailMapper {

    /**
     * Convierte un AppointmentDetail a DetailsDTO.
     * @param detail entidad a convertir
     * @return DTO equivalente o null si la entrada es null
     */
    public static DetailsDTO toDTO(AppointmentDetail detail) {
        if (detail == null) {
            return null;
        }

        DetailsDTO dto = new DetailsDTO();
        dto.setId(detail.getId());
        dto.setAppointmentId(detail.getAppointment() != null ? detail.getAppointment().getId() : null);
        dto.setDiagnosis(detail.getDiagnosis());
        dto.setPrescription(detail.getPrescription());
        dto.setNotes(detail.getNotes());
        dto.setTreatment(detail.getTreatment());
        dto.setFollowUp(detail.getFollowUp());
        return dto;
    }

    /**
     * Convierte un DetailsDTO a AppointmentDetail.
     * @param dto DTO a convertir
     * @return entidad equivalente o null si la entrada es null
     */
    public static AppointmentDetail toEntity(DetailsDTO dto) {
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

