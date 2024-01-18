package com.epam.esm.service.impl;

import com.epam.esm.core.dto.OrderDTO;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.AuthException;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private GiftCertificateRepositoryImpl giftCertificateRepository;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        int page = 1;
        int size = 10;
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll(page, size)).thenReturn(expectedOrders);

        // Act
        List<Order> result = orderService.findAll(page, size);

        // Assert
        assertEquals(expectedOrders, result);
        verify(orderRepository, times(1)).findAll(page, size);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Order expectedOrder = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(expectedOrder));

        // Act
        Order result = orderService.findById(id);

        // Assert
        assertEquals(expectedOrder, result);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.findById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO(1L, 2L);
        User user = new User();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(2L);
        giftCertificate.setPrice(BigDecimal.TEN);

        when(userRepository.findById(orderDTO.userId())).thenReturn(Optional.of(user));
        when(giftCertificateRepository.findById(orderDTO.giftCertificateId())).thenReturn(Optional.of(giftCertificate));

        Order expectedOrder = new Order(BigDecimal.TEN, LocalDateTime.now(), giftCertificate, user);
        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        // Act
        Order result = orderService.create(orderDTO, authentication);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOrder.getPrice(), result.getPrice());
        verify(userRepository, times(1)).findById(orderDTO.userId());
        verify(giftCertificateRepository, times(1)).findById(orderDTO.giftCertificateId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateUserNotFound() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO(1L, 2L);

        when(userRepository.findById(orderDTO.userId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.create(orderDTO, authentication));
        verify(userRepository, times(1)).findById(orderDTO.userId());
        verify(giftCertificateRepository, never()).findById(anyLong());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testCreateGiftCertificateNotFound() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO(1L, 2L);
        User user = new User();

        when(userRepository.findById(orderDTO.userId())).thenReturn(Optional.of(user));
        when(giftCertificateRepository.findById(orderDTO.giftCertificateId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.create(orderDTO, authentication));
        verify(userRepository, times(1)).findById(orderDTO.userId());
        verify(giftCertificateRepository, times(1)).findById(orderDTO.giftCertificateId());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testFindOrdersInfoByUserId() {
        // Arrange
        Long userId = 1L;
        int page = 1;
        int size = 10;
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(orderRepository.findOrdersInfoByUserId(userId, page, size)).thenReturn(expectedOrders);

        // Act
        List<Order> result = orderService.findOrdersInfoByUserId(userId, page, size);

        // Assert
        assertEquals(expectedOrders, result);
        verify(userRepository, times(1)).findById(userId);
        verify(orderRepository, times(1)).findOrdersInfoByUserId(userId, page, size);
    }

    @Test
    void testFindOrdersInfoByUserIdNotFound() {
        // Arrange
        Long userId = 1L;
        int page = 1;
        int size = 10;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.findOrdersInfoByUserId(userId, page, size));
        verify(userRepository, times(1)).findById(userId);
        verify(orderRepository, never()).findOrdersInfoByUserId(anyLong(), anyInt(), anyInt());
    }

    @Test
    void testFindUserOrdersNotAuthenticated() {
        // Arrange
        int page = 0;
        int size = 10;

        // Act & Assert
        AuthException exception = assertThrows(AuthException.class, () -> orderService.findUserOrders(page, size, null));
        assertEquals("You are not authorized to perform this action.", exception.getMessage());

        verify(orderRepository, never()).findOrdersInfoByUserId(anyLong(), anyInt(), anyInt());
    }

    @Test
    void testFindUserOrdersUnauthorized() {
        // Arrange
        int page = 0;
        int size = 10;
        Long userId = 1L;
        Long unauthorizedUserId = 2L;

        User user = new User();
        user.setId(unauthorizedUserId);
        user.setUsername("username");
        user.setPassword("password");
        user.setOrderList(Collections.emptyList());

        CustomUserDetails userDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        // Act & Assert
        AuthException exception = assertThrows(AuthException.class, () -> orderService.findUserOrders(page, size, authentication));
        assertEquals("You are not authorized to perform this action.", exception.getMessage());

        verify(orderRepository, never()).findOrdersInfoByUserId(userId, page, size);
    }

}