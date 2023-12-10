package com.epam.esm.repository;

import java.util.Optional;

public interface SaveAndFindByIdRepository<T> {
    T save(T entity);
    Optional<T> findById(Long id);
}
