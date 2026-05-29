package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController controller;

    @Test
    void getAllPatientsReturnsList() {
        when(patientService.getAllPatients()).thenReturn(List.of(samplePatientDto()));

        List<PatientDTO> result = controller.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("99911122A", result.getFirst().getDni());
        assertEquals("Av. Central 101", result.getFirst().getAddress());
    }

    @Test
    void getPatientByIdReturnsPatient() {
        when(patientService.findById(1)).thenReturn(samplePatientDto());

        PatientDTO result = controller.getPatientById(1);

        assertEquals("Carlos", result.getFirstName());
    }

    @Test
    void createPatientReturnsSavedPatient() {
        when(patientService.save(any(PatientDTO.class))).thenReturn(samplePatientDto());

        PatientDTO result = controller.createPatient(samplePatientDto());

        assertEquals("99911122A", result.getDni());
        assertEquals("Av. Central 101", result.getAddress());
    }

    @Test
    void updatePatientDelegatesToService() {
        when(patientService.update(eq(1), any(PatientDTO.class))).thenReturn(samplePatientDto());

        controller.updatePatient(1, samplePatientDto());

        verify(patientService).update(eq(1), any(PatientDTO.class));
    }

    @Test
    void deletePatientDelegatesToService() {
        doNothing().when(patientService).deletePatient(1);

        controller.deletePatient(1);

        verify(patientService).deletePatient(1);
    }

    @Test
    void getPatientsByAddressReturnsList() {
        when(patientService.findByAddressContaining("Central")).thenReturn(List.of(samplePatientDto()));

        List<PatientDTO> result = controller.getPatientsByAddress("Central");

        assertEquals(1, result.size());
        assertEquals("Av. Central 101", result.getFirst().getAddress());
    }

    private PatientDTO samplePatientDto() {
        return PatientDTO.builder()
                .id(1)
                .dni("99911122A")
                .firstName("Carlos")
                .secondName(null)
                .lastName("Santos")
                .secondLastName("García")
                .email("c.santos@mail.com")
                .phone("555-4000")
                .birthDate(LocalDate.of(1990, 3, 12))
                .address("Av. Central 101")
                .build();
    }

}
