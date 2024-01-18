package com.epam.esm.service.security;

import com.epam.esm.core.dto.ApiErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.epam.esm.core.constants.ErrorCodeConstants.SC_UNAUTHORIZED_ERROR;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiErrorResponseDTO errorResponse = new ApiErrorResponseDTO("Unauthorized", SC_UNAUTHORIZED_ERROR);
        String jsonErrorResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonErrorResponse);
    }


}
