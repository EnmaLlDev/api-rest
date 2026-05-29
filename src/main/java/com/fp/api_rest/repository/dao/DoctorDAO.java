package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Doctor.
 */
public interface DoctorDAO extends JpaRepository<Doctor, Integer> {
    /**
     * Busca un médico por su número de licencia.
     * @param licenseNumber número de licencia
     * @return médico encontrado, si existe
     */
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    /**
     * Busca médicos cuya especialidad contenga el término indicado (sin distinción de mayúsculas).
     * @param keyword término de búsqueda
     * @return lista de médicos coincidentes
     */
    List<Doctor> findBySpecialtyContainingIgnoreCase(String keyword);
}
