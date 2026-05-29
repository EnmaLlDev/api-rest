package com.fp.api_rest.controller;

import com.fp.api_rest.service.DoctorService;
import org.springframework.web.bind.annotation.*;
import com.fp.api_rest.model.dto.DoctorDTO;
import java.util.List;

/**
 * Controlador REST para la gestión de médicos.
 */
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Lista todos los médicos.
     * @return lista de médicos
     */
    @GetMapping("/getAll")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.findAll();
    }

    /**
     * Obtiene un médico por su ID.
     * @param id identificador del médico
     * @return médico encontrado o null
     */
    @GetMapping("/{id}")
    public DoctorDTO getDoctorById(@PathVariable Integer id) {
        return doctorService.findById(id);
    }

    /**
     * Crea un nuevo médico.
     * @param doctorDTO datos del médico
     * @return médico creado
     */
    @PostMapping("/create")
    public DoctorDTO createDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.save(doctorDTO);
    }

    /**
     * Actualiza un médico existente.
     * @param id identificador del médico
     * @param doctorDTO datos a actualizar
     */
    @PutMapping("/update/{id}")
    public void updateDoctor(@PathVariable Integer id,@RequestBody DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        System.out.println("Doctor updated");
    }

    /**
     * Elimina un médico por su ID.
     * @param id identificador del médico
     */
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteById(id);
    }

    /**
     * Busca médicos por número de licencia.
     * @param licenseNumber número de licencia
     * @return lista de médicos coincidentes
     */
    @GetMapping("/license/{licenseNumber}")
    public List<DoctorDTO> getDoctorsByLicenseNumber(@PathVariable String licenseNumber) {
        return doctorService.findByLicenseNumber(licenseNumber);
    }

    /**
     * Busca médicos por especialidad.
     * @param terms especialidad a buscar
     * @return lista de médicos coincidentes
     */
    @GetMapping("/search/{terms}")
    public List<DoctorDTO> findBySpecialtyContainingIgnoreCase(@PathVariable String terms) {
        return doctorService.findBySpecialtyContainingIgnoreCase(terms);
    }
}