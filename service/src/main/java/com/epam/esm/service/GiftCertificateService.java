package com.epam.esm.service;

import com.epam.esm.core.entity.GiftCertificate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The interface for services that manage Gift Certificate entities.
 */
public interface GiftCertificateService extends ReadableService<GiftCertificate>,
        CreatableService<GiftCertificate>, DeletableService {

    /**
     * Updates a Gift Certificate with new information.
     *
     * @param id                   The identifier of the Gift Certificate to be updated.
     * @param updatedGiftCertificate The updated Gift Certificate information.
     * @return The updated Gift Certificate.
     */
    GiftCertificate update(Long id, GiftCertificate updatedGiftCertificate);

    /**
     * Finds Gift Certificates based on specified criteria.
     *
     * @param tagNames   The list of tag names to filter by.
     * @param search     The search string for filtering by name or description.
     * @param sortBy     The field by which to sort the results.
     * @param ascending  The sorting order (true for ascending, false for descending).
     * @return A list of Gift Certificates that match the specified criteria.
     */
    List<GiftCertificate> findCertificatesByCriteria(List<String> tagNames, String search, String sortBy, boolean ascending);

    /**
     * Updates the duration of a Gift Certificate.
     *
     * @param id       The identifier of the Gift Certificate to be updated.
     * @param duration A map containing the new duration information.
     * @return The updated Gift Certificate.
     */
    GiftCertificate updateGiftCertificateDuration(Long id, Map<String, Integer> duration);

    /**
     * Updates the price of a Gift Certificate.
     *
     * @param id    The identifier of the Gift Certificate to be updated.
     * @param price A map containing the new price information.
     * @return The updated Gift Certificate.
     */
    GiftCertificate updateGiftCertificatePrice(Long id, Map<String, BigDecimal> price);
}
