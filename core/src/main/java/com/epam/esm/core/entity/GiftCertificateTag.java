package com.epam.esm.core.entity;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateTag {

    private Long id;
    private Long giftCertificateId;
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateTag that = (GiftCertificateTag) o;
        return Objects.equals(tagId, that.tagId) && Objects.equals(giftCertificateId, that.giftCertificateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, giftCertificateId);
    }
}
