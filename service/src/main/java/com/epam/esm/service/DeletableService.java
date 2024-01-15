package com.epam.esm.service;

/**
 * The interface for services that handle the deletion of entities by their identifiers.
 */
public interface DeletableService {

    /**
     * Deletes an entity by its identifier.
     *
     * @param id The identifier of the entity to be deleted.
     */
    void delete(Long id);
}
