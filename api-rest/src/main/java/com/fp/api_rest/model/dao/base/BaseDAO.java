package com.fp.api_rest.model.dao.base;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    void deleteById(ID id);
    boolean existsById(ID id);
}
