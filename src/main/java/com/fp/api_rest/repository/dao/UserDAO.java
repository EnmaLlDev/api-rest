package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad User (usuarios del sistema).
 */
public interface UserDAO extends JpaRepository<User, Long> {
    /**
     * Busca un usuario por su nombre de usuario.
     * @param username nombre de usuario
     * @return usuario encontrado, si existe
     */
    Optional<User> findByUsername(String username);
}
