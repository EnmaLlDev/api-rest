package com.fp.api_rest.controller.files;


import com.fp.api_rest.convertFiles.ConvertCsv;
import com.fp.api_rest.convertFiles.CsvProperties;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.service.PatientCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files/patients")
@ConditionalOnProperty(name = "app.data.source", havingValue = "csv")
public class PatientFilesController {

    private final PatientCsvService service;
    private final ConvertCsv convertCsv; // ya existente para import/export directo

    public PatientFilesController(PatientCsvService service, ConvertCsv convertCsv) {
        this.service = service;
        this.convertCsv = convertCsv;
    }

    @GetMapping
    public List<Patient> list() { return service.getAll(); }

    @GetMapping("/search")
    public List<Patient> search(@RequestParam String address) {
        return service.searchByAddress(address);
    }

    @PostMapping
    public ResponseEntity<Patient> upsert(@RequestBody Patient p) {
        return new ResponseEntity<>(service.upsert(p), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    public ResponseEntity<String> importConfigured() throws IOException {
        File in = new File(convertCsvPathBase(), propsNameIn());
        List<Patient> saved = convertCsv.convertAndSavePatients(in.getPath());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Importados " + saved.size() + " pacientes desde " + in.getPath());
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportConfigured() throws IOException {
        String msg = convertCsv.convertPatientsToCsvFile(new File(convertCsvPathBase(), propsNameOut()).getPath());
        return ResponseEntity.ok(msg);
    }

    private String convertCsvPathBase() { return csvProps.getRuta().getBase(); }
    private String propsNameIn() { return csvProps.getRuta().getInputFileName(); }
    private String propsNameOut() { return csvProps.getRuta().getOutputFileName(); }

    @Autowired
    private CsvProperties csvProps;
}