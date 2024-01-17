package com.epam.esm.service;

import com.epam.esm.core.dto.OrderDTO;
import com.epam.esm.core.entity.Order;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * The interface for services that manage Order entities.
 */
public interface OrderService extends ReadableService<Order> {

    /**
     * Creates a new Order based on the provided DTO.
     *
     * @param dto            The OrderDTO containing information for creating the Order.
     * @param authentication The authentication object containing details of the authenticated user making the request.
     * @return The created Order.
     */
    Order create(OrderDTO dto, Authentication authentication);

    /**
     * Finds Orders with additional information by the user's identifier.
     *
     * @param userId         The identifier of the user for whom to retrieve orders.
     * @param page           The page number (starting from 0).
     * @param size           The number of orders per page.
     * @param authentication The authentication object containing details of the authenticated user making the request.
     * @return A list of Orders with additional information for the specified user, paginated as per the provided parameters.
     */
    List<Order> findOrdersInfoByUserId(Long userId, int page, int size, Authentication authentication);
}
