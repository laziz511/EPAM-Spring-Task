package com.epam.esm.core.dto;

/**
 * Data Transfer Object (DTO) representing a response for user authentication.
 */
public record AuthenticationResponse(String accessToken, String refreshToken) {
}
