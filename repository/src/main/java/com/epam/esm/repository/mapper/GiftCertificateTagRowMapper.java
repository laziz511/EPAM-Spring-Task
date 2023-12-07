package com.epam.esm.repository.mapper;

import com.epam.esm.core.entity.GiftCertificateTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateTagRowMapper implements RowMapper<GiftCertificateTag> {

    @Override
    public GiftCertificateTag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificateTag association = new GiftCertificateTag();
        association.setId(resultSet.getLong("id"));
        association.setGiftCertificateId(resultSet.getLong("gift_certificate_id"));
        association.setTagId(resultSet.getLong("tag_id"));
        return association;
    }
}
