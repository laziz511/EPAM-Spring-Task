package com.epam.esm.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing authentication-related errors, typically for
 * scenarios where user authentication fails or is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthException extends RuntimeException {

    /**
     * Constructs an AuthException with the specified detail message.
     *
     * @param message The detail message explaining the authentication error.
     */
    public AuthException(String message) {
        super(message);
    }
}
