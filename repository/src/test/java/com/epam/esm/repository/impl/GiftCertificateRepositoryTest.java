package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;
import com.epam.esm.repository.config.DataSourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.repository.constants.GiftCertificatesRepositoryTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataSourceConfig.class)
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepositoryImpl giftCertificateRepository;

    @Test
    void getTags_correctTagList_whenGetGiftCertificates() throws GiftCertificateNotFoundException {
        assertEquals(GIFT_CERTIFICATE_LIST, giftCertificateRepository.findAll(),
                "When getting certificates, list should be equal to database values");
    }

    @Test
    void getGiftCertificate_correctGiftCertificate_whenGetGiftCertificate() throws GiftCertificateNotFoundException {
        System.out.println(GIFT_CERTIFICATE_2);
        System.out.println(giftCertificateRepository.findById(GIFT_CERTIFICATE_2.getId()).get());
        assertEquals(GIFT_CERTIFICATE_2, giftCertificateRepository.findById(GIFT_CERTIFICATE_2.getId()).orElse(new GiftCertificate()),
                "When getting certificate, it should be equal to database value");
    }

    @Test
    void getGiftCertificate_exception_whenTryToGetAbsentCertificate() {
        GiftCertificateNotFoundException exception = assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateRepository.findById(ABSENT_ID),
                "Tag should be not found and  DataNotFoundException should be thrown");
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage(), "Exception message should be " + EXCEPTION_MESSAGE);
    }

    @Test
    void updateGiftCertificate_updatedGiftCertificate_whenGiftCertificateWasUpdated() throws GiftCertificateNotFoundException {
        giftCertificateRepository.update(UPDATED_CERTIFICATE);
        assertEquals(UPDATED_CERTIFICATE, giftCertificateRepository.findById(UPDATED_CERTIFICATE.getId()).orElse(new GiftCertificate()),
                "Tag should be updated");
    }

    @Test
    void saveGiftCertificate_savedGiftCertificate_whenGiftCertificateWasSaved() throws GiftCertificateNotFoundException, GiftCertificateOperationException {
        GiftCertificate savingCertificate = NEW_GIFT_CERTIFICATE;
        giftCertificateRepository.save(savingCertificate);
        savingCertificate.setId(NEW_ID);
        assertEquals(savingCertificate, giftCertificateRepository.findById(NEW_ID).orElse(new GiftCertificate()), "Gift certificate should be saved with correct ID");
    }

    @Test
    void deleteGiftCertificate_exception_whenTryToGetDeletedGiftCertificate() throws GiftCertificateNotFoundException {
        giftCertificateRepository.delete(GIFT_CERTIFICATE_1.getId());
        assertThrows(GiftCertificateNotFoundException.class, () -> {
            giftCertificateRepository.findById(GIFT_CERTIFICATE_1.getId());
        }, "Tag should be not found and  DataNotFoundException should be thrown");
    }

    @Test
    void getGiftCertificatesByDescription_certificateList_whenGetListFilterByAllParams() throws GiftCertificateOperationException {
        assertEquals(GIFT_CERTIFICATE_LIST_WITH_TAG_NAME,
                giftCertificateRepository.findCertificatesByCriteria(TAG_NAME_VALUE, null, "name", true),
                "Actual list should be filtered by all params");
    }
}

