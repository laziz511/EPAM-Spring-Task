package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link GiftCertificateRepository} using JPA and Hibernate.
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAll(int page, int size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(root);

        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size).setMaxResults(size);

        return typedQuery.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate save(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findCertificatesByCriteria(List<String> tagNames, String search, String sortBy, boolean ascending) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);

        Predicate finalPredicate = criteriaBuilder.conjunction();
        List<Predicate> tagPredicates = new ArrayList<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                Join<GiftCertificate, Tag> tagJoin = certificateRoot.join("tags");
                Predicate tagPredicate = criteriaBuilder.equal(tagJoin.get("name"), tagName);
                tagPredicates.add(tagPredicate);
            }
            finalPredicate = criteriaBuilder.and(tagPredicates.toArray(new Predicate[0]));
        }

        if (search != null && !search.isEmpty()) {
            Predicate searchPredicate = criteriaBuilder.or(criteriaBuilder.like(certificateRoot.get("name"), "%" + search + "%"),
                    criteriaBuilder.like(certificateRoot.get("description"), "%" + search + "%"));
            finalPredicate = criteriaBuilder.and(finalPredicate, searchPredicate);
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            Expression<?> orderField = sortBy.equals("name") ? certificateRoot.get("name") : certificateRoot.get("createdDate");
            query.orderBy(ascending ? criteriaBuilder.asc(orderField) : criteriaBuilder.desc(orderField));
        }

        query.where(finalPredicate);

        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}