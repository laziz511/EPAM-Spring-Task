package com.epam.esm.repository;

import com.epam.esm.core.entity.GiftCertificateTag;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;

import java.util.List;

public interface GiftCertificateTagRepository {

    GiftCertificateTag save(GiftCertificateTag giftCertificateTag) throws GiftCertificateOperationException;

    List<GiftCertificateTag> findAssociationsByGiftCertificateId(Long giftCertificateId) throws GiftCertificateNotFoundException;
}
