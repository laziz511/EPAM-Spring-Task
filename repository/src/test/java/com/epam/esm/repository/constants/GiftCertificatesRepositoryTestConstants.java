package com.epam.esm.repository.constants;


import com.epam.esm.core.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class GiftCertificatesRepositoryTestConstants {
    public static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "Gift Certificate 1", "Description 1", BigDecimal.valueOf(180.75), 6, LocalDateTime.of(2020, 6, 1, 12, 0), LocalDateTime.of(2020, 6, 7, 12, 30), null);
    public static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "Gift Certificate 2", "Description 2", BigDecimal.valueOf(120.99), 4, LocalDateTime.of(2021, 2, 15, 8, 30), LocalDateTime.of(2021, 2, 19, 9, 45), null);
    public static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "Gift Certificate 3", "Description 3", BigDecimal.valueOf(99.99), 3, LocalDateTime.of(2023, 11, 20, 9, 0), LocalDateTime.of(2023, 11, 22, 9, 15), null);
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
    public static final GiftCertificate UPDATED_CERTIFICATE = new GiftCertificate(3L, "UPDATED Gift Certificate", "Description for updated gift", BigDecimal.valueOf(999.99), 12, LocalDateTime.of(2023, 11, 20, 9, 0), LocalDateTime.of(2099, 12, 12, 12, 12), null);
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST_WITH_TAG_NAME = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2);
    public static final GiftCertificate NEW_GIFT_CERTIFICATE = new GiftCertificate(4L, "New Gift Certificate", "Description for New Gift Certificate", BigDecimal.valueOf(150.66), 10, LocalDateTime.of(2022, 10, 10, 18, 0), LocalDateTime.of(2022, 10, 10, 18, 30), null);
    public static final Long NEW_ID = 4L;
    public static final Long ABSENT_ID = 80L;
    public static final String EXCEPTION_MESSAGE = "Gift certificate not found with id: " + ABSENT_ID;
    public static final String TAG_NAME_VALUE = "Tag 1";

}
