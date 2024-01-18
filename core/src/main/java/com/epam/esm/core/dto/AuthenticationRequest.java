package com.epam.esm.core.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) representing a request for user authentication.
 */
public record AuthenticationRequest(
        @NotBlank(message = "username cannot be null") String username,
        @NotBlank(message = "password cannot be null") String password) {
}
