package com.fp.api_rest.model.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseDAO<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
