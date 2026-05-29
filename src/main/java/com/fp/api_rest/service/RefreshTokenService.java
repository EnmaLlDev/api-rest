package com.fp.api_rest.service;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import com.fp.api_rest.repository.dao.RefreshTokenDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Servicio para la gestión de tokens de refresco.
 */
@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenDAO refreshTokenDAO;

    @Value("${app.security.jwt.refresh-token-expiration-ms}")
    private long refreshExpirationMs;

    public RefreshTokenService(RefreshTokenDAO refreshTokenDAO) {
        this.refreshTokenDAO = refreshTokenDAO;
    }

    /**
     * Crea un nuevo refresh token para un usuario.
     * @param user usuario asociado
     * @return refresh token guardado
     */
    public RefreshToken createToken(User user) {
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusNanos(refreshExpirationMs * 1_000_000))
                .revoked(false)
                .build();
        return refreshTokenDAO.save(token);
    }

    /**
     * Verifica que el refresh token sea válido y no esté revocado.
     * @param tokenValue valor del token
     * @return refresh token verificado
     */
    public RefreshToken verifyUsableToken(String tokenValue) {
        RefreshToken token = refreshTokenDAO.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token invalido"));

        if (token.isRevoked() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expirado o revocado");
        }
        return token;
    }

    /**
     * Revoca un refresh token.
     * @param tokenValue valor del token a revocar
     */
    public void revokeToken(String tokenValue) {
        refreshTokenDAO.findByToken(tokenValue).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenDAO.save(token);
        });
    }

    /**
     * Revoca todos los refresh tokens de un usuario.
     * @param user usuario cuyos tokens se revocarán
     */
    public void revokeAllByUser(User user) {
        refreshTokenDAO.deleteByUser(user);
    }
}
