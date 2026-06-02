package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.DetailsDTO;
import com.fp.api_rest.service.DetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para la gestión de detalles clínicos de citas.
 */
@RestController
@RequestMapping("/api/details")
public class DetailsController {

    private final DetailsService appointmentDetailService;

    public DetailsController(DetailsService appointmentDetailService) {
        this.appointmentDetailService = appointmentDetailService;
    }

    /**
     * Lista todos los detalles clínicos.
     * @return lista de detalles
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<DetailsDTO>> getAllAppointmentDetails() {
        List<DetailsDTO> details = appointmentDetailService.findAll();
        return ResponseEntity.ok(details);
    }

    /**
     * Obtiene un detalle clínico por su ID.
     * @param id identificador del detalle
     * @return detalle encontrado o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetailsDTO> getAppointmentDetailById(@PathVariable Integer id) {
        DetailsDTO dto = appointmentDetailService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Obtiene los detalles clínicos de una cita específica.
     * @param appointmentId identificador de la cita
     * @return lista de detalles o 404
     */
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<DetailsDTO>> getAppointmentDetailsByAppointmentId(@PathVariable Integer appointmentId) {
        List<DetailsDTO> dtos = appointmentDetailService.findByAppointmentId(appointmentId);
        if (dtos == null || dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }

    /**
     * Crea un nuevo detalle clínico.
     * @param appointmentDetail datos del detalle
     * @return detalle creado o error
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAppointmentDetail(@RequestBody DetailsDTO appointmentDetail) {
        try {
            DetailsDTO created = appointmentDetailService.save(appointmentDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating appointment detail: " + ex.getMessage());
        }
    }

    /**
     * Actualiza un detalle clínico existente.
     * @param id identificador del detalle
     * @param appointmentDetail datos a actualizar
     * @return detalle actualizado o error
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAppointmentDetail(@PathVariable Integer id, @RequestBody DetailsDTO appointmentDetail) {
        try {
            DetailsDTO updated = appointmentDetailService.update(id, appointmentDetail);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating appointment detail");
        }
    }

    /**
     * Elimina un detalle clínico por su ID.
     * @param id identificador del detalle
     * @return respuesta sin contenido o error
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointmentDetail(@PathVariable Integer id) {
        try {
            appointmentDetailService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting appointment detail");
        }
    }

    /**
     * Obtiene los detalles clínicos del usuario autenticado.
     * Si el usuario tiene rol DOCTOR devuelve los detalles de sus citas.
     * Si tiene rol PATIENT devuelve los detalles de sus propias citas.
     * @param authentication autenticación del usuario
     * @return lista de detalles filtrada por rol
     */
    @GetMapping("/my")
    public ResponseEntity<List<DetailsDTO>> getMyDetails(Authentication authentication) {
        String email = authentication.getName();
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("DOCTOR")
                        || a.getAuthority().equalsIgnoreCase("ROLE_DOCTOR"));
        List<DetailsDTO> details = isDoctor
                ? appointmentDetailService.findByDoctorForEmail(email)
                : appointmentDetailService.findByPatientEmail(email);
        return ResponseEntity.ok(details);
    }
}