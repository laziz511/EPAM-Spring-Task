package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.GiftCertificateTag;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.mapper.GiftCertificateTagRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {

    private static final String INSERT = "INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_ASSOCIATIONS_BY_CERTIFICATE_ID = "SELECT * FROM gift_certificate_tags WHERE gift_certificate_id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM gift_certificate_tags WHERE id = ?";

    private final RowMapper<GiftCertificateTag> giftCertificateTagRowMapper = new GiftCertificateTagRowMapper();
    private final JdbcTemplate jdbcTemplate;

    @Override
    public GiftCertificateTag save(GiftCertificateTag giftCertificateTag) throws GiftCertificateOperationException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> prepareInsertStatement(connection, giftCertificateTag), keyHolder);

            giftCertificateTag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

            return giftCertificateTag;
        } catch (DataAccessException e) {
            log.error("Error occurred while saving gift certificate tag association", e);
            throw new GiftCertificateOperationException("Error occurred while saving gift certificate tag association", e);
        }
    }

    @Override
    public List<GiftCertificateTag> findAssociationsByGiftCertificateId(Long giftCertificateId) throws GiftCertificateNotFoundException {
        try {
            return jdbcTemplate.query(SELECT_ASSOCIATIONS_BY_CERTIFICATE_ID, giftCertificateTagRowMapper, giftCertificateId);
        } catch (DataAccessException e) {
            log.error("Error occurred while getting associations for gift certificate with ID: {}", giftCertificateId, e);
            throw new GiftCertificateNotFoundException("Error occurred while getting associations for gift certificate", e);
        }
    }

    @Override
    public Optional<GiftCertificateTag> findById(Long associationId) throws GiftCertificateNotFoundException {
        try {
            GiftCertificateTag association = jdbcTemplate.queryForObject(SELECT_BY_ID, giftCertificateTagRowMapper, associationId);
            return Optional.ofNullable(association);
        } catch (DataAccessException e) {
            log.error("Error occurred while getting association with ID: {}", associationId, e);
            throw new GiftCertificateNotFoundException("Error occurred while getting association", e);
        }
    }


    private PreparedStatement prepareInsertStatement(Connection connection, GiftCertificateTag giftCertificateTag) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"id"});
        ps.setLong(1, giftCertificateTag.getGiftCertificateId());
        ps.setLong(2, giftCertificateTag.getTagId());
        return ps;
    }
}
