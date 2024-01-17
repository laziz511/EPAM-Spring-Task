package com.epam.esm.service.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.core.exception.OperationException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.core.constants.ErrorMessageConstants.*;
import static com.epam.esm.core.utils.Validator.validatePageAndSize;

/**
 * Implementation of the {@link GiftCertificateService} interface.
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        validatePageAndSize(page, size, GiftCertificate.class);

        return giftCertificateRepository.findAll(page, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GIFT_CERTIFICATE_NOT_FOUND_ERROR_MESSAGE + id, GiftCertificate.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate entity) {

        entity.setCreatedDate(LocalDateTime.now());
        List<Tag> tags = entity.getTags();

        if (tags == null) {
            tags = new ArrayList<>();
        }
        for (Tag tag : tags) {
            if (tag.getGiftCertificates() == null) tag.setGiftCertificates(new HashSet<>());

            tag.getGiftCertificates().add(entity);
        }
        entity.setTags(tags);
        return giftCertificateRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate updatedGiftCertificate) {

        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate.isEmpty())
            throw new NotFoundException(GIFT_CERTIFICATE_NOT_FOUND_ERROR_MESSAGE + id, GiftCertificate.class);

        updatedGiftCertificate.setId(id);
        updatedGiftCertificate.setCreatedDate(giftCertificate.get().getCreatedDate());
        updatedGiftCertificate.setLastUpdatedDate(LocalDateTime.now());

        try {
            return giftCertificateRepository.update(updatedGiftCertificate);
        } catch (Exception ex) {
            throw new OperationException(GIFT_CERTIFICATE_FAILED_UPDATE_ERROR_MESSAGE + ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
        if (giftCertificate.isEmpty())
            throw new NotFoundException(GIFT_CERTIFICATE_NOT_FOUND_ERROR_MESSAGE + id, GiftCertificate.class);

        giftCertificateRepository.delete(giftCertificate.get());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findCertificatesByCriteria(List<String> tagNames, String search, String sortBy, boolean ascending) {
        return giftCertificateRepository.findCertificatesByCriteria(tagNames, search, sortBy, ascending);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GiftCertificate updateGiftCertificateDuration(Long id, Map<String, Integer> requestBody) {
        final String DURATION = "duration";
        if (!requestBody.containsKey(DURATION))
            throw new OperationException(REQUEST_BODY_MISSING_FIELD_ERROR_MESSAGE + DURATION, GiftCertificate.class);

        Integer durationResult = requestBody.get(DURATION);

        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GIFT_CERTIFICATE_NOT_FOUND_ERROR_MESSAGE + id, GiftCertificate.class));

        giftCertificate.setDuration(durationResult);
        giftCertificate.setCreatedDate(giftCertificate.getCreatedDate());
        giftCertificate.setLastUpdatedDate(LocalDateTime.now());

        return giftCertificateRepository.save(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GiftCertificate updateGiftCertificatePrice(Long id, Map<String, BigDecimal> requestBody) {
        final String PRICE = "price";
        if (!requestBody.containsKey(PRICE))
            throw new OperationException(REQUEST_BODY_MISSING_FIELD_ERROR_MESSAGE + PRICE, GiftCertificate.class);

        BigDecimal price = requestBody.get(PRICE);

        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GIFT_CERTIFICATE_NOT_FOUND_ERROR_MESSAGE + id, GiftCertificate.class));

        giftCertificate.setPrice(price);
        giftCertificate.setCreatedDate(giftCertificate.getCreatedDate());
        giftCertificate.setLastUpdatedDate(LocalDateTime.now());

        return giftCertificateRepository.save(giftCertificate);
    }
}
