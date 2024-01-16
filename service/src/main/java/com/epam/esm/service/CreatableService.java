package com.epam.esm.service;

/**
 * The interface for services that handle the creation of entities.
 *
 * @param <T> The type of entity that this service can create.
 */
public interface CreatableService<T> {

    /**
     * Creates a new entity.
     *
     * @param entity The entity to be created.
     * @return The created entity.
     */
    T create(T entity);
}
