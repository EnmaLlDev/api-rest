package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad RefreshToken.
 */
public interface RefreshTokenDAO extends JpaRepository<RefreshToken, Long> {
    /**
     * Busca un refresh token por su valor.
     * @param token valor del token
     * @return token encontrado, si existe
     */
    Optional<RefreshToken> findByToken(String token);
    /**
     * Elimina todos los refresh tokens asociados a un usuario.
     * @param usuario cuyos tokens se eliminarán
     */
    void deleteByUser(User user);
}
