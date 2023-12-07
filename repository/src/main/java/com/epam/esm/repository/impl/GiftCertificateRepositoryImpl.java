package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapper.GiftCertificateRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private static final String SELECT_BY_ID = "SELECT * FROM gift_certificates WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM gift_certificates";
    private static final String INSERT = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE gift_certificates SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM gift_certificates WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) throws GiftCertificateOperationException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> prepareInsertStatement(connection, giftCertificate), keyHolder);

            giftCertificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

            return giftCertificate;
        } catch (DataAccessException e) {
            log.error("Error occurred while saving gift certificate", e);
            throw new GiftCertificateOperationException("Error occurred while saving gift certificate", e);
        }
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws GiftCertificateNotFoundException {
        try {
            GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SELECT_BY_ID, new GiftCertificateRowMapper(), id);
            return Optional.ofNullable(giftCertificate);
        } catch (RuntimeException e) {
            log.error("Error occurred while getting gift certificate with id = {}", id, e);
            throw new GiftCertificateNotFoundException("Gift certificate not found with id: " + id, e);
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws GiftCertificateOperationException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new GiftCertificateRowMapper());
        } catch (RuntimeException e) {
            log.error("Error occurred while getting all gift certificates", e);
            throw new GiftCertificateNotFoundException("Error occurred while getting all gift certificates", e);
        }
    }

    @Override
    public void update(GiftCertificate giftCertificate) throws GiftCertificateNotFoundException, GiftCertificateOperationException {
        try {
            int updatedRows = jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getLastUpdateDate(),
                    giftCertificate.getId());

            if (updatedRows == 0) {
                throw new GiftCertificateNotFoundException("Gift certificate not found with ID: " + giftCertificate.getId());
            }
        } catch (DataAccessException e) {
            log.error("Error occurred while updating gift certificate with ID: {}", giftCertificate.getId(), e);
            throw new GiftCertificateOperationException("Error occurred while updating gift certificate", e);
        }
    }

    @Override
    public void delete(Long id) throws GiftCertificateOperationException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (RuntimeException e) {
            log.error("Error occurred while deleting gift certificate with id = {}", id, e);
            throw new GiftCertificateOperationException("Error occurred while deleting gift certificate", e);
        }
    }

    @Override
    public List<GiftCertificate> findCertificatesByCriteria(String tagName, String search, String sortBy, boolean ascending) throws GiftCertificateOperationException {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM gift_certificates gc WHERE 1=1");

            List<Object> queryParams = new ArrayList<>();

            if (tagName != null) {
                addTagCriteria(queryBuilder, queryParams, tagName);
            }

            if (search != null && !search.isEmpty()) {
                addSearchCriteria(queryBuilder, queryParams, search);
            }

            if (sortBy != null) {
                addSortingCriteria(queryBuilder, sortBy, ascending);
            }

            return jdbcTemplate.query(queryBuilder.toString(), queryParams.toArray(), new GiftCertificateRowMapper());
        } catch (DataAccessException e) {
            log.error("Error occurred while searching for certificates by criteria", e);
            throw new GiftCertificateOperationException("Error occurred while searching for certificates by criteria", e);
        }
    }

    private void addTagCriteria(StringBuilder queryBuilder, List<Object> queryParams, String tagName) {
        queryBuilder.append(" AND EXISTS (SELECT 1 FROM gift_certificate_tags gct JOIN tags t ON gct.tag_id = t.id WHERE gct.gift_certificate_id = gc.id AND t.name = ?)");
        queryParams.add(tagName);
    }

    private void addSearchCriteria(StringBuilder queryBuilder, List<Object> queryParams, String search) {
        queryBuilder.append(" AND (gc.name ILIKE ? OR gc.description ILIKE ?)");
        queryParams.add("%" + search + "%");
        queryParams.add("%" + search + "%");
    }

    private void addSortingCriteria(StringBuilder queryBuilder, String sortBy, boolean ascending) {
        String sortField = sortBy.equals("name") ? "gc.name" : "gc.create_date";
        queryBuilder.append(" ORDER BY ").append(sortField);

        if (ascending) {
            queryBuilder.append(" ASC");
        } else {
            queryBuilder.append(" DESC");
        }
    }

    private PreparedStatement prepareInsertStatement(Connection connection, GiftCertificate giftCertificate) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"id"});
        ps.setString(1, giftCertificate.getName());
        ps.setString(2, giftCertificate.getDescription());
        ps.setBigDecimal(3, giftCertificate.getPrice());
        ps.setInt(4, giftCertificate.getDuration());
        ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
        ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));

        return ps;

    }
}
