package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.AppointmentDTO;
import com.fp.api_rest.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de citas médicas.
 */
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Lista todas las citas.
     * @return lista de citas
     */
    @GetMapping("/getAll")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll();
    }

    /**
     * Obtiene una cita por su ID.
     * @param id identificador de la cita
     * @return cita encontrada o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable int id) {
        AppointmentDTO dto = appointmentService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Crea una nueva cita.
     * @param appointment datos de la cita
     * @return cita creada
     */
    @PostMapping("/create")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointment) {
        AppointmentDTO created = appointmentService.save(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza una cita existente.
     * @param id identificador de la cita
     * @param appointment datos a actualizar
     * @return cita actualizada o error
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointment) {
        try {
            AppointmentDTO updated = appointmentService.update(id, appointment);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Elimina una cita por su ID.
     * @param id identificador de la cita
     * @return respuesta sin contenido
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todas las citas de un doctor por su ID.
     * @param doctorId identificador del doctor
     * @return lista de citas del doctor
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(@PathVariable Integer doctorId) {
        return ResponseEntity.ok(appointmentService.findByDoctorId(doctorId));
    }

    /**
     * Obtiene las citas del usuario autenticado.
     * Si el usuario tiene rol DOCTOR devuelve sus citas como médico.
     * Si tiene rol PATIENT devuelve sus citas como paciente.
     * @param authentication autenticación del usuario
     * @return lista de citas filtrada por rol
     */
    @GetMapping("/my")
    public ResponseEntity<List<AppointmentDTO>> getMyAppointments(Authentication authentication) {
        String email = authentication.getName();
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("DOCTOR")
                        || a.getAuthority().equalsIgnoreCase("ROLE_DOCTOR"));
        List<AppointmentDTO> appointments = isDoctor
                ? appointmentService.findByDoctorForEmail(email)
                : appointmentService.findByPatientEmail(email);
        return ResponseEntity.ok(appointments);
    }
}