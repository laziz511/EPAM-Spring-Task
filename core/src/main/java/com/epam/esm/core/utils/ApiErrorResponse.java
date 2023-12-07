package com.epam.esm.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private final String errorMessage;
    private final int errorCode;
    private final HttpStatus httpStatus;
}
