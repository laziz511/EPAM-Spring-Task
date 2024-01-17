package com.epam.esm.service.security;

import com.epam.esm.core.dto.ApiErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.esm.core.constants.ErrorCodeConstants.ACCESS_DENIED_ERROR;

/**
 * Custom implementation of AccessDeniedHandler for handling access denied exceptions.
 * Sends a forbidden response with an error message in case of an AccessDeniedException.
 */
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    /**
     * Handles access denied exception by sending a forbidden response with an error message.
     *
     * @param request               The request that resulted in an <code>AccessDeniedException</code>.
     * @param response              The response to be populated with the error details.
     * @param accessDeniedException The exception that caused the access denied.
     * @throws IOException In case of an I/O error when writing the response.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ApiErrorResponseDTO apiErrorResponseDTO = new ApiErrorResponseDTO("Access denied", ACCESS_DENIED_ERROR);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiErrorResponseDTO));
    }
}
