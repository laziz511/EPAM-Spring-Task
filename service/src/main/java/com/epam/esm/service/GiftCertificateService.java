package com.epam.esm.service;

import com.epam.esm.core.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate findGiftCertificateById(Long id);

    List<GiftCertificate> findAllGiftCertificates();

    GiftCertificate updateGiftCertificate(Long id, GiftCertificate updatedGiftCertificate);

    void deleteGiftCertificate(Long id);

    List<GiftCertificate> findCertificatesByCriteria(String tagName, String search, String sortBy, boolean ascending);
}
