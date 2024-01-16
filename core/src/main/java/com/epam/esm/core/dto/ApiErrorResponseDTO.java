package com.epam.esm.core.dto;

/**
 * Data Transfer Object (DTO) representing an API error response.
 */
public record ApiErrorResponseDTO(String errorMessage, int errorCode) {
}
