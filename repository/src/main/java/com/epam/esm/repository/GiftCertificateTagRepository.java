package com.epam.esm.repository;

import com.epam.esm.core.entity.GiftCertificateTag;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;

import java.util.List;

public interface GiftCertificateTagRepository extends SaveAndFindByIdRepository<GiftCertificateTag> {
    List<GiftCertificateTag> findAssociationsByGiftCertificateId(Long giftCertificateId) throws GiftCertificateNotFoundException;
}
