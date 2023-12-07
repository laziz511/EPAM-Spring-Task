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
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {

    private static final String INSERT = "INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_ASSOCIATIONS_BY_CERTIFICATE_ID = "SELECT * FROM gift_certificate_tags WHERE gift_certificate_id = ?";

    private final RowMapper<GiftCertificateTag> giftCertificateTagRowMapper = new GiftCertificateTagRowMapper();
    private final JdbcTemplate jdbcTemplate;


    @Override
    public GiftCertificateTag save(GiftCertificateTag giftCertificateTag) throws GiftCertificateOperationException {
        try {
            jdbcTemplate.update(INSERT, giftCertificateTag.getGiftCertificateId(), giftCertificateTag.getTagId());
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
}
