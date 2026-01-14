package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.AppointmentDetailDTO;
import com.fp.api_rest.service.AppointmentDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment-detail")
public class AppointmentDetailController {

    private final AppointmentDetailService appointmentDetailService;

    public AppointmentDetailController(AppointmentDetailService appointmentDetailService) {
        this.appointmentDetailService = appointmentDetailService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AppointmentDetailDTO>> getAllAppointmentDetails() {
        List<AppointmentDetailDTO> details = appointmentDetailService.findAll();
        return ResponseEntity.ok(details);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailDTO> getAppointmentDetailById(@PathVariable Integer id) {
        AppointmentDetailDTO dto = appointmentDetailService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<AppointmentDetailDTO> getAppointmentDetailByAppointmentId(@PathVariable Integer appointmentId) {
        AppointmentDetailDTO dto = appointmentDetailService.findByAppointmentId(appointmentId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAppointmentDetail(@RequestBody AppointmentDetailDTO appointmentDetail) {
        try {
            AppointmentDetailDTO created = appointmentDetailService.save(appointmentDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating appointment detail: " + ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAppointmentDetail(@PathVariable Integer id, @RequestBody AppointmentDetailDTO appointmentDetail) {
        try {
            AppointmentDetailDTO updated = appointmentDetailService.update(id, appointmentDetail);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating appointment detail");
        }
    }

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
}