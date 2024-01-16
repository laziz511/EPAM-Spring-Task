package com.epam.esm.service.impl;

import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.core.exception.OperationException;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

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

}
