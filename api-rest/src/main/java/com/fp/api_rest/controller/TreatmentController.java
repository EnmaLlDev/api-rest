package com.fp.api_rest.controller;
import com.fp.api_rest.model.dto.TreatmentDTO;
import com.fp.api_rest.service.TreatmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping
    public List<TreatmentDTO> getAllDoctors() {
        return treatmentService.findAll();
    }

    @GetMapping("/{id}")
    public TreatmentDTO getDoctorById(@PathVariable Integer id) {
        return treatmentService.findById(id);
    }

    @PostMapping
    public TreatmentDTO createDoctor(@RequestBody TreatmentDTO treatmentDTO) {
        return treatmentService.save(treatmentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTreatment(@PathVariable Integer id) {
        treatmentService.deleteById(id);
    }
}
