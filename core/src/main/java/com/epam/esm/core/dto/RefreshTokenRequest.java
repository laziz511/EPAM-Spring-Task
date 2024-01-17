package com.epam.esm.core.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) representing a request to refresh an access token.
 */
public record RefreshTokenRequest(
        @NotBlank(message = "username cannot be null") String username,
        @NotBlank(message = "refreshToken cannot be null") String refreshToken) {
}
