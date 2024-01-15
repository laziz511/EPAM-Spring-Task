package com.epam.esm.web.exception;

import com.epam.esm.core.dto.ApiErrorResponseDTO;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.core.exception.OperationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.core.constants.ErrorCodeConstants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<?>, Integer> NOT_FOUND_ERROR_CODES = new HashMap<>();
    private static final Map<Class<?>, Integer> OPERATION_ERROR_CODES = new HashMap<>();

    static {
        NOT_FOUND_ERROR_CODES.put(GiftCertificate.class, GIFT_CERTIFICATE_NOT_FOUND);
        NOT_FOUND_ERROR_CODES.put(Order.class, ORDER_NOT_FOUND);
        NOT_FOUND_ERROR_CODES.put(Tag.class, TAG_NOT_FOUND);
        NOT_FOUND_ERROR_CODES.put(User.class, USER_NOT_FOUND);

        OPERATION_ERROR_CODES.put(GiftCertificate.class, GIFT_CERTIFICATE_OPERATION_ERROR);
        OPERATION_ERROR_CODES.put(Order.class, ORDER_OPERATION_ERROR);
        OPERATION_ERROR_CODES.put(Tag.class, TAG_OPERATION_ERROR);
        OPERATION_ERROR_CODES.put(User.class, USER_OPERATION_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleNotFoundException(NotFoundException ex, HttpServletRequest req) {
        int errorCode = NOT_FOUND_ERROR_CODES.getOrDefault(ex.getExceptionClass(), 0);
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, errorCode);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleOperationException(OperationException ex, HttpServletRequest req) {
        int errorCode = OPERATION_ERROR_CODES.getOrDefault(ex.getExceptionClass(), 0);
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST, VALIDATION_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Required field is missing in JSON object";
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(message, FIELD_MISSING_IN_PATCH_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    private ResponseEntity<ApiErrorResponseDTO> buildErrorResponse(String message, HttpStatus status, int errorCode) {
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(message, errorCode);
        return ResponseEntity.status(status).body(apiError);
    }
}
