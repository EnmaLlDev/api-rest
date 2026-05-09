package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.DetailsDTO;
import com.fp.api_rest.service.DetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetailsControllerTest {

    @Mock
    private DetailsService detailsService;

    @InjectMocks
    private DetailsController controller;

    @Test
    void getAllAppointmentDetailsReturnsList() {
        when(detailsService.findAll()).thenReturn(List.of(sampleDetailsDto()));

        ResponseEntity<List<DetailsDTO>> response = controller.getAllAppointmentDetails();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hipertensión", response.getBody().getFirst().getDiagnosis());
    }

    @Test
    void getAppointmentDetailByIdReturnsDetail() {
        when(detailsService.findById(1)).thenReturn(sampleDetailsDto());

        ResponseEntity<DetailsDTO> response = controller.getAppointmentDetailById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        DetailsDTO body = response.getBody();
        assertEquals("2025-11-18", body.getFollowUp());
    }

    @Test
    void getAppointmentDetailByIdReturnsNotFoundWhenMissing() {
        when(detailsService.findById(1)).thenReturn(null);

        ResponseEntity<DetailsDTO> response = controller.getAppointmentDetailById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getDetailsByAppointmentIdReturnsList() {
        when(detailsService.findByAppointmentId(1)).thenReturn(List.of(sampleDetailsDto()));

        ResponseEntity<List<DetailsDTO>> response = controller.getAppointmentDetailsByAppointmentId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getFirst().getAppointmentId());
    }

    @Test
    void getDetailsByAppointmentIdReturnsNotFoundWhenEmpty() {
        when(detailsService.findByAppointmentId(1)).thenReturn(List.of());

        ResponseEntity<List<DetailsDTO>> response = controller.getAppointmentDetailsByAppointmentId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createAppointmentDetailReturnsCreatedDetail() {
        when(detailsService.save(any(DetailsDTO.class))).thenReturn(sampleDetailsDto());

        ResponseEntity<?> response = controller.createAppointmentDetail(sampleDetailsDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        DetailsDTO created = (DetailsDTO) response.getBody();
        assertEquals("Hipertensión", created.getDiagnosis());
    }

    @Test
    void createAppointmentDetailReturnsNotFoundWhenAppointmentDoesNotExist() {
        when(detailsService.save(any(DetailsDTO.class)))
                .thenThrow(new EntityNotFoundException("Appointment not found with id 1"));

        ResponseEntity<?> response = controller.createAppointmentDetail(sampleDetailsDto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createAppointmentDetailReturnsConflictWhenPersistenceFails() {
        when(detailsService.save(any(DetailsDTO.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate key"));

        ResponseEntity<?> response = controller.createAppointmentDetail(sampleDetailsDto());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void createAppointmentDetailReturnsBadRequestWhenInvalidData() {
        when(detailsService.save(any(DetailsDTO.class)))
                .thenThrow(new IllegalArgumentException("invalid"));

        ResponseEntity<?> response = controller.createAppointmentDetail(sampleDetailsDto());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateAppointmentDetailReturnsOk() {
        when(detailsService.update(eq(1), any(DetailsDTO.class))).thenReturn(sampleDetailsDto());

        ResponseEntity<?> response = controller.updateAppointmentDetail(1, sampleDetailsDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        DetailsDTO updated = (DetailsDTO) response.getBody();
        assertEquals("Hipertensión", updated.getDiagnosis());
    }

    @Test
    void updateAppointmentDetailReturnsNotFoundWhenMissing() {
        when(detailsService.update(eq(1), any(DetailsDTO.class)))
                .thenThrow(new EntityNotFoundException("AppointmentDetail not found with id 1"));

        ResponseEntity<?> response = controller.updateAppointmentDetail(1, sampleDetailsDto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteAppointmentDetailReturnsNoContent() {
        doNothing().when(detailsService).deleteById(1);

        ResponseEntity<?> response = controller.deleteAppointmentDetail(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(detailsService).deleteById(1);
    }

    @Test
    void deleteAppointmentDetailReturnsNotFoundWhenMissing() {
        doThrow(new EntityNotFoundException("AppointmentDetail not found with id 1"))
                .when(detailsService).deleteById(1);

        ResponseEntity<?> response = controller.deleteAppointmentDetail(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private DetailsDTO sampleDetailsDto() {
        return DetailsDTO.builder()
                .id(1)
                .appointmentId(1)
                .diagnosis("Hipertensión")
                .prescription("Lisinopril 10mg")
                .notes("Controlar presión arterial")
                .treatment("Cambios en el estilo de vida")
                .followUp("2025-11-18")
                .build();
    }
}
