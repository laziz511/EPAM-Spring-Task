package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);

        query.select(tagRoot);

        TypedQuery<Tag> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag save(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Tag tag) throws NotFoundException {
        entityManager.remove(tag);
    }

    @Override
    public List<Tag> findMostUsedTagOfUserWithHighestOrderCost(Long userId) {
        String findMostUsedTagOfUserQuery = """
                WITH UserTagSummary AS (
                    SELECT
                        t.id AS tag_id,
                        t.name AS tag_name,
                        SUM(o.price) AS total_price
                    FROM
                        orders o
                    JOIN
                        users u ON o.user_id = u.id
                    JOIN
                        gift_certificates g ON g.id = o.gift_certificate_id
                    JOIN
                        gift_certificate_tag gct ON g.id = gct.gift_id
                    JOIN
                        tags t ON t.id = gct.tag_id
                    WHERE
                        u.id = :userId
                    GROUP BY
                        t.id, t.name
                )
                SELECT
                    tag_id AS id,
                    tag_name AS name
                FROM
                    UserTagSummary
                WHERE
                    total_price = (SELECT MAX(total_price) FROM UserTagSummary)
                ORDER BY
                    total_price DESC
                """;

        Query query = entityManager.createNativeQuery(findMostUsedTagOfUserQuery, Tag.class)
                .setParameter("userId", userId);

        return query.getResultList();
    }


}
