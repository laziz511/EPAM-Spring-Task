package com.epam.esm.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a bad request or invalid operation scenario.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class OperationException extends RuntimeException {

    /**
     * The class of the exception.
     */
    private final transient Object exceptionClass;

    /**
     * Constructs an OperationException with the specified message and exception class.
     *
     * @param message        The detail message.
     * @param exceptionClass The class of the exception.
     */
    public OperationException(String message, Object exceptionClass) {
        super(message);
        this.exceptionClass = exceptionClass;
    }
}
