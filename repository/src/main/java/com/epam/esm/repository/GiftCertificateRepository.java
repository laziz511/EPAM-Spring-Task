package com.epam.esm.repository;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;

import java.util.List;


public interface GiftCertificateRepository extends SaveAndFindByIdRepository<GiftCertificate> {
    List<GiftCertificate> findAll() throws GiftCertificateNotFoundException;
    void update(GiftCertificate giftCertificate) throws GiftCertificateNotFoundException, GiftCertificateOperationException;
    void delete(Long id) throws GiftCertificateOperationException;
    List<GiftCertificate> findCertificatesByCriteria(String tagName, String search, String sortBy, boolean ascending) throws GiftCertificateOperationException;
}
