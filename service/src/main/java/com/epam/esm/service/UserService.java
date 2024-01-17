package com.epam.esm.service;

import com.epam.esm.core.dto.AuthenticationRequest;
import com.epam.esm.core.dto.AuthenticationResponse;
import com.epam.esm.core.dto.RegisterRequest;
import com.epam.esm.core.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The interface for services that manage User entities.
 * It extends the ReadableService interface to provide basic read operations for User entities.
 */
public interface UserService extends ReadableService<User> {

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerRequest The registration details for the new user.
     * @return The registered User entity.
     */
    User register(RegisterRequest registerRequest);

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param authenticationRequest The credentials for user authentication.
     * @return An AuthenticationResponse containing access and refresh tokens upon successful authentication.
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    /**
     * Refreshes the access token for a user based on the provided refresh token.
     *
     * @param request  The HTTP request containing the refresh token.
     * @param response The HTTP response used to set the new access token.
     * @return An AuthenticationResponse containing the new access and refresh tokens.
     */
    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}
