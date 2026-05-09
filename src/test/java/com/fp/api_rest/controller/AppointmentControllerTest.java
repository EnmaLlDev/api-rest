package com.fp.api_rest.controller;

import com.fp.api_rest.model.StateAppointment;
import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController controller;

    @Test
    void getAllAppointmentsReturnsList() {
        when(appointmentService.findAll()).thenReturn(List.of(sampleAppointmentDto()));

        List<AppointmentDTO> result = controller.getAllAppointments();

        assertEquals(1, result.size());
        assertEquals("Chequeo general", result.get(0).getReason());
        assertEquals(StateAppointment.SCHEDULED, result.get(0).getStatus());
    }

    @Test
    void getAppointmentByIdReturnsAppointment() {
        when(appointmentService.findById(1)).thenReturn(sampleAppointmentDto());

        ResponseEntity<AppointmentDTO> response = controller.getAppointmentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("LIC001", response.getBody().getDoctor().getLicenseNumber());
    }

    @Test
    void getAppointmentByIdReturnsNotFoundWhenMissing() {
        when(appointmentService.findById(1)).thenReturn(null);

        ResponseEntity<AppointmentDTO> response = controller.getAppointmentById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createAppointmentReturnsCreatedAppointment() {
        when(appointmentService.save(any(AppointmentDTO.class))).thenReturn(sampleAppointmentDto());

        ResponseEntity<AppointmentDTO> response = controller.createAppointment(sampleAppointmentDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Chequeo general", response.getBody().getReason());
    }

    @Test
    void updateAppointmentReturnsNotFoundWhenServiceThrows() {
        when(appointmentService.update(eq(1), any(AppointmentDTO.class)))
                .thenThrow(new EntityNotFoundException("Appointment not found with id 1"));

        ResponseEntity<AppointmentDTO> response = controller.updateAppointment(1, sampleAppointmentDto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateAppointmentReturnsBadRequestWhenUnexpectedErrorOccurs() {
        when(appointmentService.update(eq(1), any(AppointmentDTO.class)))
                .thenThrow(new IllegalStateException("boom"));

        ResponseEntity<AppointmentDTO> response = controller.updateAppointment(1, sampleAppointmentDto());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteAppointmentReturnsNoContent() {
        doNothing().when(appointmentService).deleteById(1);

        ResponseEntity<Void> response = controller.deleteAppointment(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(appointmentService).deleteById(1);
    }

    private AppointmentDTO sampleAppointmentDto() {
        return AppointmentDTO.builder()
                .id(1)
                .dateTime(LocalDate.of(2025, 11, 10))
                .patient(PatientDTO.builder()
                        .id(1)
                        .dni("99911122A")
                        .firstName("Carlos")
                        .lastName("Santos")
                        .email("c.santos@mail.com")
                        .build())
                .doctor(DoctorDTO.builder()
                        .id(1)
                        .dni("12345678A")
                        .firstName("Luis")
                        .lastName("Gómez")
                        .email("l.gomez@clinic.com")
                        .licenseNumber("LIC001")
                        .specialty("Cardiología")
                        .build())
                .reason("Chequeo general")
                .status(StateAppointment.SCHEDULED)
                .build();
    }
}
