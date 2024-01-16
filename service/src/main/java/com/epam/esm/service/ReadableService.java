package com.epam.esm.service;

import java.util.List;

/**
 * The interface for services that handle the retrieval of entities.
 *
 * @param <T> The type of entity that this service can retrieve.
 */
public interface ReadableService<T> {

    /**
     * Retrieves a paginated list of entities.
     *
     * @param page The page number (starting from 0).
     * @param size The number of entities per page.
     * @return A list of entities for the specified page and size.
     */
    List<T> findAll(int page, int size);

    /**
     * Retrieves an entity by its identifier.
     *
     * @param id The identifier of the entity to be retrieved.
     * @return The retrieved entity, or {@code null} if not found.
     */
    T findById(Long id);
}
