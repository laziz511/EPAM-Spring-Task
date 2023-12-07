package com.epam.esm.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
