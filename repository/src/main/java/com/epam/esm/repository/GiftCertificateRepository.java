package com.epam.esm.repository;

import com.epam.esm.core.entity.GiftCertificate;

import java.util.List;

/**
 * The interface for repositories that manage Gift Certificate entities.
 */
public interface GiftCertificateRepository extends CreatableRepository<GiftCertificate>,
        ReadableRepository<GiftCertificate>, DeletableRepository<GiftCertificate> {

    /**
     * Updates a Gift Certificate with new information.
     *
     * @param giftCertificate The Gift Certificate to be updated.
     * @return The updated Gift Certificate.
     */
    GiftCertificate update(GiftCertificate giftCertificate);

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
}
