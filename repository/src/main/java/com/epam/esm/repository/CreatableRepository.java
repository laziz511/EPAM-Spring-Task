package com.epam.esm.repository;

/**
 * The interface for repositories that handle the creation of entities.
 *
 * @param <T> The type of entity that this repository can create.
 */
public interface CreatableRepository<T> {

    /**
     * Saves a new entity.
     *
     * @param entity The entity to be saved.
     * @return The saved entity.
     */
    T save(T entity);
}
