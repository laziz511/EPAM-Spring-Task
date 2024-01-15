package com.epam.esm.service.impl;

import com.epam.esm.core.dto.OrderDTO;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.core.constants.ErrorMessageConstants.*;
import static com.epam.esm.core.utils.Validator.validatePageAndSize;

/**
 * Implementation of the {@link OrderService} interface.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final GiftCertificateRepositoryImpl giftCertificateRepository;
    private final UserRepositoryImpl userRepository;
    private final OrderRepository orderRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findAll(int page, int size) {
        validatePageAndSize(page, size, Order.class);
        return orderRepository.findAll(page, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_ERROR_MESSAGE + id, Order.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order create(OrderDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + dto.userId(), Order.class));

        GiftCertificate giftCertificate = giftCertificateRepository.findById(dto.giftCertificateId())
                .orElseThrow(() -> new NotFoundException(GIFT_CERTIFICATE_NOT_FOUND_ERROR_MESSAGE + dto.giftCertificateId(), Order.class));

        Order orderToSave = new Order(giftCertificate.getPrice(), LocalDateTime.now(), giftCertificate, user);
        return orderRepository.save(orderToSave);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findOrdersInfoByUserId(Long userId, int page, int size) {
        validatePageAndSize(page, size, Order.class);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + userId, Order.class);

        return orderRepository.findOrdersInfoByUserId(userId, page, size);
    }

}
