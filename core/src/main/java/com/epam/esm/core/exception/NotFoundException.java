package com.epam.esm.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a resource not found scenario.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class NotFoundException extends RuntimeException {

    /**
     * The class of the exception.
     */
    private final transient Object exceptionClass;

    /**
     * Constructs a NotFoundException with the specified message and exception class.
     *
     * @param message        The detail message.
     * @param exceptionClass The class of the exception.
     */
    public NotFoundException(String message, Object exceptionClass) {
        super(message);
        this.exceptionClass = exceptionClass;
    }
}
