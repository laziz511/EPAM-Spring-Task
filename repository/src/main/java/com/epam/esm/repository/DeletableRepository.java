package com.epam.esm.repository;

/**
 * The interface for repositories that handle the deletion of entities.
 *
 * @param <T> The type of entity that this repository can delete.
 */
public interface DeletableRepository<T> {

    /**
     * Deletes an entity.
     *
     * @param entity The entity to be deleted.
     */
    void delete(T entity);
}
