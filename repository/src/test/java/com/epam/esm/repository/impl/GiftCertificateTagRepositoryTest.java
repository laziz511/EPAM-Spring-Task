package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.GiftCertificateTag;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;
import com.epam.esm.core.exception.TagNotFoundException;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.config.DataSourceConfig;
import com.epam.esm.repository.constants.GiftCertificateTagRepositoryTestConstants.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.epam.esm.repository.constants.GiftCertificateTagRepositoryTestConstants.*;
import static com.epam.esm.repository.constants.TagRepositoryTestConstants.TAG_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataSourceConfig.class)
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GiftCertificateTagRepositoryTest {

    @Autowired
    private GiftCertificateTagRepository giftCertificateTagRepository;

    @Test
    void saveAssociation_savedAssociation_whenAssociationWasSaved() throws GiftCertificateNotFoundException, GiftCertificateOperationException {
        GiftCertificateTag savingAssociation = NEW_GIFT_TAG;
        giftCertificateTagRepository.save(savingAssociation);
        savingAssociation.setId(NEW_ID);
        assertEquals(savingAssociation, giftCertificateTagRepository.findById(NEW_ID), "Association should be saved with correct ID");
    }

    @Test
    void findAssociationsByGiftCertificateId_correctAssociationList_whenFindAssociationsByGiftCertificateId()
            throws GiftCertificateNotFoundException {
        List<GiftCertificateTag> associations = giftCertificateTagRepository.findAssociationsByGiftCertificateId(GIFT_CERTIFICATE_1_ID);
        Assertions.assertEquals(GIFT_CERTIFICATE_TAG_LIST, associations, "When finding associations, association list should be equal to database values");
    }

    @Test
    void findAssociationsByGiftCertificateId_exceptionThrown_whenInvalidGiftCertificateId() throws GiftCertificateNotFoundException {
        assertThrows(GiftCertificateNotFoundException.class, () -> {
            giftCertificateTagRepository.findById(INVALID_GIFT_CERTIFICATE_ID);
        }, "Tag should be not found and  DataNotFoundException should be thrown");
    }
}
