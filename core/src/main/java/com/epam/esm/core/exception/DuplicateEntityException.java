package com.epam.esm.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a scenario where an attempt to create or update
 * a resource results in a duplicate entity conflict.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DuplicateEntityException extends RuntimeException {

    /**
     * Constructs a DuplicateEntityException with the specified detail message.
     *
     * @param message The detail message explaining the duplicate entity conflict.
     */
    public DuplicateEntityException(String message) {
        super(message);
    }
}
