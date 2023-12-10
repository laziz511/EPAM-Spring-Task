package com.epam.esm.service;

import com.epam.esm.core.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends CrudService<GiftCertificate, Long> {
    GiftCertificate updateGiftCertificate(Long id, GiftCertificate updatedGiftCertificate);
    List<GiftCertificate> findCertificatesByCriteria(String tagName, String search, String sortBy, boolean ascending);
}
