package com.epam.esm.constants;

import com.epam.esm.core.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.constants.TagTestConstants.TAG_1;
import static com.epam.esm.constants.TagTestConstants.TAG_2;

public class GiftCertificatesTestConstants {
    public static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "gift_certificate_1", "description 1",  BigDecimal.valueOf(150.5), 5,
            LocalDateTime.of(2018, 1, 1, 0, 0),
           null, Arrays.asList(TAG_1, TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "gift_certificate_2", "description 2",BigDecimal.valueOf(150.5), 5,
            LocalDateTime.of(2018, 2, 1, 0, 0),
            LocalDateTime.of(2018, 2, 5, 0, 5), Arrays.asList(TAG_1, TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "gift_certificate_3", "description 3", BigDecimal.valueOf(140), 4,
            LocalDateTime.of(2018, 3, 1, 0, 0),
            LocalDateTime.of(2018, 3, 9, 0, 5), null);
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
    public static final String NAME_VALUE = "Gift Certificate";
}
