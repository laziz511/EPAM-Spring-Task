package com.epam.esm.repository;

import com.epam.esm.core.entity.User;

import java.util.Optional;

/**
 * The interface for repositories that manage User entities.
 * It extends the ReadableRepository interface to provide basic read operations for User entities.
 */
public interface UserRepository extends ReadableRepository<User> {

    /**
     * Saves a new User entity.
     *
     * @param user The User entity to be saved.
     * @return The saved User entity.
     */
    User save(User user);

    /**
     * Checks if a User with the specified email already exists.
     *
     * @param email The email to check for existence.
     * @return True if a User with the email already exists; false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a User by the specified username.
     *
     * @param username The username of the User to be found.
     * @return An Optional containing the found User, or empty if not found.
     */
    Optional<User> findByUsername(String username);
}
