package com.fp.api_rest.service;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import com.fp.api_rest.repository.dao.RefreshTokenDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenDAO refreshTokenDAO;

    @Value("${app.security.jwt.refresh-token-expiration-ms}")
    private long refreshExpirationMs;

    public RefreshTokenService(RefreshTokenDAO refreshTokenDAO) {
        this.refreshTokenDAO = refreshTokenDAO;
    }

    public RefreshToken createToken(User user) {
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusNanos(refreshExpirationMs * 1_000_000))
                .revoked(false)
                .build();
        return refreshTokenDAO.save(token);
    }

    public RefreshToken verifyUsableToken(String tokenValue) {
        RefreshToken token = refreshTokenDAO.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token invalido"));

        if (token.isRevoked() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expirado o revocado");
        }
        return token;
    }

    public void revokeToken(String tokenValue) {
        refreshTokenDAO.findByToken(tokenValue).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenDAO.save(token);
        });
    }

    public void revokeAllByUser(User user) {
        refreshTokenDAO.deleteByUser(user);
    }
}
