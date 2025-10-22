package com.fp.api_rest.model.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
