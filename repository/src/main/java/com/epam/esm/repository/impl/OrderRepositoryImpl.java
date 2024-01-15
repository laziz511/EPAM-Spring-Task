package com.epam.esm.repository.impl;

import com.epam.esm.core.entity.Order;
import com.epam.esm.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAll(int page, int size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);

        query.select(orderRoot);

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public Order save(Order entity) {
        entityManager.persist(entity);
        return entity;
    }


    @Override
    public List<Order> findOrdersInfoByUserId(Long userId, int page, int size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);

        query.select(orderRoot)
                .where(criteriaBuilder.equal(orderRoot.get("user").get("id"), userId));

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

}
