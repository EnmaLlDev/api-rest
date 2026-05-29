package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Role.
 */
public interface RoleDAO extends JpaRepository<Role, Long> {
    /**
     * Busca un rol por su nombre.
     * @param name nombre del rol
     * @return rol encontrado, si existe
     */
    Optional<Role> findByName(String name);
}
