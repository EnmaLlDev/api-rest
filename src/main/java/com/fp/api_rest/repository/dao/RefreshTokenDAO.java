package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.RefreshToken;
import com.fp.api_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenDAO extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
