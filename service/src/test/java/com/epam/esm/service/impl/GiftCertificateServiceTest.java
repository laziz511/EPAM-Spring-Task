package com.epam.esm.service.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    private GiftCertificateService giftCertificateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository);
    }

    @Test
    void testFindAll() {
        // Arrange
        int page = 1;
        int size = 10;
        List<GiftCertificate> expectedCertificates = new ArrayList<>();
        when(giftCertificateRepository.findAll(page, size)).thenReturn(expectedCertificates);

        // Act
        List<GiftCertificate> result = giftCertificateService.findAll(page, size);

        // Assert
        assertEquals(expectedCertificates, result);
        verify(giftCertificateRepository, times(1)).findAll(page, size);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        GiftCertificate expectedCertificate = new GiftCertificate();
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(expectedCertificate));

        // Act
        GiftCertificate result = giftCertificateService.findById(id);

        // Assert
        assertEquals(expectedCertificate, result);
        verify(giftCertificateRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> giftCertificateService.findById(id));
        verify(giftCertificateRepository, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        // Arrange
        GiftCertificate certificateToCreate = new GiftCertificate();
        certificateToCreate.setName("Test Certificate");
        certificateToCreate.setCreatedDate(LocalDateTime.now());

        when(giftCertificateRepository.save(any(GiftCertificate.class))).thenReturn(certificateToCreate);

        // Act
        GiftCertificate result = giftCertificateService.create(certificateToCreate);

        // Assert
        assertNotNull(result);
        assertEquals(certificateToCreate.getName(), result.getName());
        verify(giftCertificateRepository, times(1)).save(any(GiftCertificate.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        GiftCertificate existingCertificate = new GiftCertificate();
        existingCertificate.setId(id);
        existingCertificate.setName("Existing Certificate");

        GiftCertificate updatedCertificate = new GiftCertificate();
        updatedCertificate.setId(id);
        updatedCertificate.setName("Updated Certificate");

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(existingCertificate));
        when(giftCertificateRepository.update(any(GiftCertificate.class))).thenReturn(updatedCertificate);

        // Act
        GiftCertificate result = giftCertificateService.update(id, updatedCertificate);

        // Assert
        assertNotNull(result);
        assertEquals(updatedCertificate.getName(), result.getName());
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, times(1)).update(updatedCertificate);
    }

    @Test
    void testUpdateNotFound() {
        // Arrange
        Long id = 1L;
        GiftCertificate updatedCertificate = new GiftCertificate();
        updatedCertificate.setId(id);
        updatedCertificate.setName("Updated Certificate");

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> giftCertificateService.update(id, updatedCertificate));
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, never()).update(any(GiftCertificate.class));
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        GiftCertificate existingCertificate = new GiftCertificate();
        existingCertificate.setId(id);
        existingCertificate.setName("Existing Certificate");

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(existingCertificate));

        // Act
        giftCertificateService.delete(id);

        // Assert
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, times(1)).delete(existingCertificate);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        Long id = 1L;

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> giftCertificateService.delete(id));
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, never()).delete(any(GiftCertificate.class));
    }

    @Test
    void testFindCertificatesByCriteria() {
        // Arrange
        List<String> tagNames = Arrays.asList("Tag 1", "Tag 2");
        String search = "certificate";
        String sortBy = "name";
        boolean ascending = true;

        List<GiftCertificate> expectedCertificates = Arrays.asList(
                new GiftCertificate(), new GiftCertificate());

        when(giftCertificateRepository.findCertificatesByCriteria(tagNames, search, sortBy, ascending))
                .thenReturn(expectedCertificates);

        // Act
        List<GiftCertificate> result = giftCertificateService.findCertificatesByCriteria(tagNames, search, sortBy, ascending);

        // Assert
        assertEquals(expectedCertificates, result);
        verify(giftCertificateRepository, times(1)).findCertificatesByCriteria(tagNames, search, sortBy, ascending);
    }

    @Test
    void testUpdateGiftCertificateDuration() {
        // Arrange
        Long id = 1L;
        Map<String, Integer> requestBody = Collections.singletonMap("duration", 10);

        GiftCertificate existingCertificate = new GiftCertificate();
        existingCertificate.setId(id);
        existingCertificate.setName("Existing Certificate");

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(existingCertificate));
        when(giftCertificateRepository.save(any(GiftCertificate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        GiftCertificate result = giftCertificateService.updateGiftCertificateDuration(id, requestBody);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getDuration());
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, times(1)).save(any(GiftCertificate.class));
    }

    @Test
    void testUpdateGiftCertificateDurationNotFound() {
        // Arrange
        Long id = 1L;
        Map<String, Integer> requestBody = Collections.singletonMap("duration", 10);

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> giftCertificateService.updateGiftCertificateDuration(id, requestBody));
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, never()).save(any(GiftCertificate.class));
    }

    @Test
    void testUpdateGiftCertificatePrice() {
        // Arrange
        Long id = 1L;
        Map<String, BigDecimal> requestBody = Collections.singletonMap("price", BigDecimal.valueOf(20.99));

        GiftCertificate existingCertificate = new GiftCertificate();
        existingCertificate.setId(id);
        existingCertificate.setName("Existing Certificate");

        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(existingCertificate));
        when(giftCertificateRepository.save(any(GiftCertificate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        GiftCertificate result = giftCertificateService.updateGiftCertificatePrice(id, requestBody);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(20.99), result.getPrice());
        verify(giftCertificateRepository, times(1)).findById(id);
        verify(giftCertificateRepository, times(1)).save(any(GiftCertificate.class));
    }


}
