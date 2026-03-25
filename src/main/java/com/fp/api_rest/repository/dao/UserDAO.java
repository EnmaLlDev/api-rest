package com.fp.api_rest.repository.dao;

import com.fp.api_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
