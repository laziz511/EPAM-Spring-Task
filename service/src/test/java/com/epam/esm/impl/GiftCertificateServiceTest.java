package com.epam.esm.impl;

import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateRepository mockGiftCertificateRepository;

    @Mock
    private GiftCertificateTagRepository mockGiftCertificateTagRepository;

    @Mock
    private TagService mockTagService;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void findGiftCertificateById_ShouldReturnExpectedGiftCertificate_WhenGiftCertificateExists() throws GiftCertificateNotFoundException {
        // Arrange
        when(mockGiftCertificateRepository.findById(GIFT_CERTIFICATE_1.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));

        // Act
        var actualGiftCertificate = giftCertificateService.findById(GIFT_CERTIFICATE_1.getId());

        // Assert
        assertEquals("Actual gift certificate should be equal to expected", GIFT_CERTIFICATE_1, actualGiftCertificate);
    }

    @Test
    void findAllGiftCertificates_ShouldReturnExpectedGiftCertificateList() throws GiftCertificateNotFoundException {
        // Arrange
        when(mockGiftCertificateRepository.findAll()).thenReturn(GIFT_CERTIFICATE_LIST);

        // Act
        var actualGiftCertificates = giftCertificateService.findAll();

        // Assert
        assertEquals("Actual gift certificate list should be equal to expected", GIFT_CERTIFICATE_LIST, actualGiftCertificates);
    }

    @Test
    void findCertificatesByCriteria_ShouldReturnExpectedGiftCertificateList_WhenGettingGiftCertificatesWithNullParams() throws GiftCertificateNotFoundException {
        // Arrange
        when(mockGiftCertificateRepository.findCertificatesByCriteria(null, null, null, false)).thenReturn(GIFT_CERTIFICATE_LIST);

        // Act
        var actualGiftCertificates = giftCertificateService.findCertificatesByCriteria(null, null, null, false);

        // Assert
        assertEquals("Actual gift certificate list should be equal to expected", GIFT_CERTIFICATE_LIST, actualGiftCertificates);
    }

    @Test
    void findCertificatesByCriteria_ShouldReturnExpectedGiftCertificateList_WhenGettingGiftCertificatesWithParams() throws GiftCertificateNotFoundException {
        // Arrange
        when(mockGiftCertificateRepository.findCertificatesByCriteria(null, NAME_VALUE, null, false)).thenReturn(GIFT_CERTIFICATE_LIST);

        // Act
        var actualGiftCertificates = giftCertificateService.findCertificatesByCriteria(null, NAME_VALUE, null, false);

        // Assert
        assertEquals("Actual gift certificate list should be equal to expected", GIFT_CERTIFICATE_LIST, actualGiftCertificates);
    }
}
