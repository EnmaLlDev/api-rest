package com.fp.api_rest.controller;

import com.fp.api_rest.model.Doctor;
import com.fp.api_rest.model.dto.DoctorDTO;
import com.fp.api_rest.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController controller;

    @Test
    void getAllDoctorsReturnsList() {
        when(doctorService.findAll()).thenReturn(List.of(sampleDoctorDto()));

        List<DoctorDTO> result = controller.getAllDoctors();

        assertEquals(1, result.size());
        assertEquals("Luis", result.getFirst().getFirstName());
        assertEquals("LIC001", result.getFirst().getLicenseNumber());
    }

    @Test
    void getDoctorByIdReturnsDoctor() {
        when(doctorService.findById(1)).thenReturn(sampleDoctorDto());

        DoctorDTO result = controller.getDoctorById(1);

        assertEquals(1, result.getId());
        assertEquals("Cardiología", result.getSpecialty());
    }

    @Test
    void createDoctorReturnsCreatedDoctor() {
        when(doctorService.save(any(DoctorDTO.class))).thenReturn(sampleDoctorDto());

        DoctorDTO result = controller.createDoctor(sampleDoctorDto());

        assertEquals("12345678A", result.getDni());
        assertEquals("LIC001", result.getLicenseNumber());
    }

    @Test
    void updateDoctorDelegatesToService() {
        when(doctorService.update(eq(1), any(DoctorDTO.class))).thenReturn(sampleDoctorEntity());

        controller.updateDoctor(1, sampleDoctorDto());

        verify(doctorService).update(eq(1), any(DoctorDTO.class));
    }

    @Test
    void deleteDoctorDelegatesToService() {
        doNothing().when(doctorService).deleteById(1);

        controller.deleteDoctor(1);

        verify(doctorService).deleteById(1);
    }

    @Test
    void findDoctorsByLicenseNumberReturnsList() {
        when(doctorService.findByLicenseNumber("LIC001")).thenReturn(List.of(sampleDoctorDto()));

        List<DoctorDTO> result = controller.getDoctorsByLicenseNumber("LIC001");

        assertEquals(1, result.size());
        assertEquals("LIC001", result.getFirst().getLicenseNumber());
    }

    @Test
    void findDoctorsBySpecialtyReturnsList() {
        when(doctorService.findBySpecialtyContainingIgnoreCase("cardio")).thenReturn(List.of(sampleDoctorDto()));

        List<DoctorDTO> result = controller.findBySpecialtyContainingIgnoreCase("cardio");

        assertEquals(1, result.size());
        assertEquals("Cardiología", result.getFirst().getSpecialty());
    }

    private DoctorDTO sampleDoctorDto() {
        return DoctorDTO.builder()
                .id(1)
                .dni("12345678A")
                .firstName("Luis")
                .secondName("Andrés")
                .lastName("Gómez")
                .secondLastName("Martínez")
                .email("l.gomez@clinic.com")
                .phone("555-1010")
                .licenseNumber("LIC001")
                .specialty("Cardiología")
                .build();
    }

    private Doctor sampleDoctorEntity() {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setDni("12345678A");
        doctor.setFirstName("Luis");
        doctor.setLastName("Gómez");
        doctor.setEmail("l.gomez@clinic.com");
        doctor.setLicenseNumber("LIC001");
        doctor.setSpecialty("Cardiología");
        return doctor;
    }
}

