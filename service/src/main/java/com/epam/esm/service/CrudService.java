package com.epam.esm.service;

import java.util.List;

public interface CrudService<T, V> {
    T create(T entity);
    T findById(V id);
    List<T> findAll();
    void delete(V id);
}
