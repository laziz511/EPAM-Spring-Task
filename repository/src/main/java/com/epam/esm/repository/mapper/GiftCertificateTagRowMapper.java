package com.epam.esm.repository.mapper;

import com.epam.esm.core.entity.GiftCertificateTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.Column.*;

public class GiftCertificateTagRowMapper implements RowMapper<GiftCertificateTag> {

    @Override
    public GiftCertificateTag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificateTag association = new GiftCertificateTag();
        association.setId(resultSet.getLong(ID));
        association.setGiftCertificateId(resultSet.getLong(GiftCertificateTagsTable.GIFT_CERTIFICATE_ID));
        association.setTagId(resultSet.getLong(GiftCertificateTagsTable.TAG_ID));
        return association;
    }
}
