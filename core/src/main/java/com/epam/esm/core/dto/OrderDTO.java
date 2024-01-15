package com.epam.esm.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) representing an Order.
 */
public record OrderDTO(
        @NotNull(message = "User ID cannot be null") @Positive(message = "User ID must be a positive number") Long userId,
        @NotNull(message = "Gift Certificate ID cannot be null") @Positive(message = "Gift Certificate ID must be a positive number") Long giftCertificateId) {
}
