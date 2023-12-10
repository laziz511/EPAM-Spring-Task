package com.epam.esm.repository.mapper;

import com.epam.esm.core.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.Column.*;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getLong(ID));
        giftCertificate.setName(resultSet.getString(NAME));
        giftCertificate.setDescription(resultSet.getString(GiftCertificateTable.DESCRIPTION));
        giftCertificate.setPrice(resultSet.getBigDecimal(GiftCertificateTable.PRICE));
        giftCertificate.setDuration(resultSet.getInt(GiftCertificateTable.DURATION));
        giftCertificate.setCreateDate(resultSet.getTimestamp(GiftCertificateTable.CREATE_DATE).toLocalDateTime());
        giftCertificate.setLastUpdateDate(resultSet.getTimestamp(GiftCertificateTable.LAST_UPDATE_DATE).toLocalDateTime());
        return giftCertificate;
    }
}
