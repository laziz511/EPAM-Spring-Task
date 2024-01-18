package com.epam.esm.service.impl;

import com.epam.esm.core.dto.AuthenticationRequest;
import com.epam.esm.core.dto.AuthenticationResponse;
import com.epam.esm.core.dto.RegisterRequest;
import com.epam.esm.core.entity.Role;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.DuplicateEntityException;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.core.constants.ErrorMessageConstants.*;
import static com.epam.esm.core.utils.Validator.validatePageAndSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll(int page, int size) {
        validatePageAndSize(page, size, User.class);

        return userRepository.findAll(page, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + id, User.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public User register(RegisterRequest registerRequest) {
        validateDuplicateEmail(registerRequest.email());
        validateDuplicateUsername(registerRequest.username());

        User newUser = new User();
        newUser.setUsername(registerRequest.username());
        newUser.setEmail(registerRequest.email());
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        newUser.setRole(Role.USER);

        return userRepository.save(newUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.username(),
                authenticationRequest.password()));

        User user = userRepository.findByUsername(authenticationRequest.username())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + authenticationRequest.username(), User.class));

        return jwtUtil.generateToken(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring(7);
            return jwtUtil.refreshToken(refreshToken);
        }
        return null;
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEntityException(USER_EMAIL_ALREADY_EXISTS_ERROR_MESSAGE);
        }
    }

    private void validateDuplicateUsername(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new DuplicateEntityException(USERNAME_ALREADY_EXISTS_ERROR_MESSAGE);
        });
    }
}
