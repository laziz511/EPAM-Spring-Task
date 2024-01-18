package com.epam.esm.service.impl;

import com.epam.esm.core.dto.AuthenticationRequest;
import com.epam.esm.core.dto.AuthenticationResponse;
import com.epam.esm.core.dto.RegisterRequest;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.DuplicateEntityException;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.core.exception.OperationException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        int page = 1;
        int size = 10;
        List<User> expectedUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll(page, size)).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.findAll(page, size);

        // Assert
        assertEquals(expectedUsers, result);
        verify(userRepository, times(1)).findAll(page, size);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        User expectedUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.findById(id);

        // Assert
        assertEquals(expectedUser, result);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.findById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testFindAllWithInvalidPageAndSize() {
        // Arrange
        int page = -1;
        int size = 0;

        // Act & Assert
        assertThrows(OperationException.class, () -> userService.findAll(page, size));
        verifyNoInteractions(userRepository);
    }

    @Test
    void testRegister() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("testemail@example.com", "test_user", "password");
        User newUser = new User();
        newUser.setUsername("test_user");
        newUser.setEmail("testemail@example.com");
        newUser.setPassword("encodedPassword");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User result = userService.register(registerRequest);

        // Assert
        assertEquals(newUser, result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("password");
    }

    @Test
    void testRegisterWithDuplicateEmail() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("testemail@example.com", "test_user", "password");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> userService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterWithDuplicateUsername() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("testemail@example.com", "test_user", "password");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> userService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAuthenticate() {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test_user", "password");
        User existingUser = new User();
        existingUser.setUsername("test_user");
        existingUser.setPassword("encodedPassword");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtUtil.generateToken(any(User.class))).thenReturn(new AuthenticationResponse("accessToken", "refreshToken"));

        // Act
        AuthenticationResponse result = userService.authenticate(authenticationRequest);

        // Assert
        assertEquals(new AuthenticationResponse("accessToken", "refreshToken"), result);
    }

    @Test
    void testRefreshToken() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader(anyString())).thenReturn("Bearer refreshToken");
        when(jwtUtil.refreshToken(anyString())).thenReturn(new AuthenticationResponse("newAccessToken", "newRefreshToken"));

        // Act
        AuthenticationResponse result = userService.refreshToken(request, response);

        // Assert
        assertEquals(new AuthenticationResponse("newAccessToken", "newRefreshToken"), result);
    }
}
