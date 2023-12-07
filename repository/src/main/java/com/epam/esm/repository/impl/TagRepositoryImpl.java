package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.core.exception.GiftCertificateOperationException;
import com.epam.esm.core.exception.TagNotFoundException;
import com.epam.esm.core.exception.TagOperationException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private static final String INSERT = "INSERT INTO tags (name) VALUES (?)";
    private static final String SELECT_BY_ID = "SELECT * FROM tags WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM tags WHERE name = ?";
    private static final String SELECT_ALL = "SELECT * FROM tags";
    private static final String DELETE = "DELETE FROM tags WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Tag save(Tag tag) throws TagOperationException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"id"});
                ps.setString(1, tag.getName());
                return ps;
            }, keyHolder);

            tag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

            return tag;
        } catch (DataAccessException e) {
            log.error("Error occurred while saving tag", e);
            throw new TagOperationException("Error occurred while saving tag", e);
        }
    }


    @Override
    public Optional<Tag> findById(Long id) throws TagNotFoundException {
        try {
            Tag tag = jdbcTemplate.queryForObject(SELECT_BY_ID, new TagRowMapper(), id);
            return Optional.ofNullable(tag);
        } catch (RuntimeException e) {
            log.error("Error occurred while getting tag with id = {}", id, e);
            throw new TagNotFoundException("Tag not found with id: " + id, e);
        }
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try {
            Tag tag = jdbcTemplate.queryForObject(SELECT_BY_NAME, new TagRowMapper(), name);
            return Optional.ofNullable(tag);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll() throws TagNotFoundException {
        try {
            return jdbcTemplate.query(SELECT_ALL, new TagRowMapper());
        } catch (RuntimeException e) {
            log.error("Error occurred while getting all tags", e);
            throw new TagNotFoundException("Error occurred while getting all tags", e);
        }
    }

    @Override
    public void delete(Long id) throws TagOperationException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (RuntimeException e) {
            log.error("Error occurred while deleting tag with id = {}", id, e);
            throw new TagOperationException("Error occurred while deleting tag", e);
        }
    }

}
