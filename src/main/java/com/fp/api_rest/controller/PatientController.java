package com.fp.api_rest.controller;

import com.fp.api_rest.model.dto.PatientDTO;
import com.fp.api_rest.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de pacientes.
 */
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * Lista todos los pacientes.
     * @return lista de pacientes
     */
    @GetMapping("/getAll")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    /**
     * Obtiene un paciente por su ID.
     * @param id identificador del paciente
     * @return paciente encontrado o null
     */
    @GetMapping("/get/{id}")
    public PatientDTO getPatientById(@PathVariable int id) {
        return patientService.findById(id);
    }

    /**
     * Crea un nuevo paciente.
     * @param patientDTO datos del paciente
     * @return paciente creado
     */
    @PostMapping("/create")
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.save(patientDTO);
    }

    /**
     * Actualiza un paciente existente.
     * @param id identificador del paciente
     * @param patientDTO datos a actualizar
     * @return paciente actualizado
     */
    @PutMapping("/update/{id}")
    public PatientDTO updatePatient(@PathVariable Integer id, @RequestBody PatientDTO patientDTO) {
        return patientService.update(id, patientDTO);
    }

    /**
     * Elimina un paciente por su ID.
     * @param id identificador del paciente
     */
    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }

    /**
     * Busca pacientes por dirección.
     * @param address dirección a buscar
     * @return lista de pacientes coincidentes
     */
    @GetMapping("/address/{address}")
    public List<PatientDTO> getPatientsByAddress(@PathVariable("address") String address) {
        return patientService.findByAddressContaining(address);
    }

    /**
     * Obtiene los datos del paciente autenticado.
     * @param authentication autenticación del usuario
     * @return datos del paciente o 404
     */
    @GetMapping("/me")
    public ResponseEntity<PatientDTO> getMyData(Authentication authentication) {
        String email = authentication.getName();
        PatientDTO patient = patientService.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }
}
