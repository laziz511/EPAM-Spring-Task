package com.epam.esm.repository;

import com.epam.esm.core.entity.Order;

import java.util.List;

/**
 * The interface for repositories that manage Order entities.
 */
public interface OrderRepository extends CreatableRepository<Order>, ReadableRepository<Order> {

    /**
     * Finds Orders with additional information by the user's identifier.
     *
     * @param userId The identifier of the user for whom to retrieve orders.
     * @param page   The page number (starting from 0).
     * @param size   The number of orders per page.
     * @return A list of Orders with additional information for the specified user, paginated as per the provided parameters.
     */
    List<Order> findOrdersInfoByUserId(Long userId, int page, int size);
}
