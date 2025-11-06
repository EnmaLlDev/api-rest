package com.fp.api_rest.repository.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseDAO<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    List<T> saveAll(List<T> entities);
    void deleteById(ID id);

}
