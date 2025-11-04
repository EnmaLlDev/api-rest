package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.DiagnosticDTO;
import com.fp.api_rest.service.DiagnosticService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }
    @GetMapping("/getAll")
    public List<DiagnosticDTO> getAllDiagnostics() {
        return diagnosticService.findAll();
    }
    @GetMapping("/{id}")
    public DiagnosticDTO getDiagnosticById(@PathVariable Integer id) {
        return diagnosticService.findById(id);
    }

    @PostMapping( "/create")
    public DiagnosticDTO createDiagnostic(@RequestBody DiagnosticDTO diagnostic) {
        return diagnosticService.save(diagnostic);
    }

    @PutMapping("/update/{id}")
    public DiagnosticDTO updateDiagnostic(@RequestBody DiagnosticDTO diagnostic) {
        return diagnosticService.save(diagnostic);

    }

    @DeleteMapping("delete/{id}")
    public void deleteDiagnostic(@PathVariable Integer id) {
        diagnosticService.deleteById(id);
    }
}
