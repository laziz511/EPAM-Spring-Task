package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface for repositories that handle the retrieval of entities.
 *
 * @param <T> The type of entity that this repository can retrieve.
 */
public interface ReadableRepository<T> {

    /**
     * Finds an entity by its identifier.
     *
     * @param id The identifier of the entity to be retrieved.
     * @return An Optional containing the retrieved entity, or empty if not found.
     */
    Optional<T> findById(Long id);

    /**
     * Retrieves a paginated list of entities.
     *
     * @param page The page number (starting from 0).
     * @param size The number of entities per page.
     * @return A list of entities for the specified page and size.
     */
    List<T> findAll(int page, int size);
}
