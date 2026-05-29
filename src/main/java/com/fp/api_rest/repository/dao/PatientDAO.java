package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Patient.
 */
public interface PatientDAO extends JpaRepository<Patient, Integer> {
    /**
     * Busca pacientes cuya dirección contenga el texto indicado.
     * @param address texto de dirección a buscar
     * @return lista de pacientes coincidentes
     */
    List<Patient> findByAddressContaining(String address);
    /**
     * Elimina todos los pacientes registrados.
     */
    void deleteAll();
    /**
     * Busca un paciente por su correo electrónico.
     * @param email correo electrónico
     * @return paciente encontrado, si existe
     */
    Optional<Patient> findByEmail(String email);
}
