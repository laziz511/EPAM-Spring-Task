package com.epam.esm.web.controller;

import com.epam.esm.core.dto.OrderDTO;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.model.OrderModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.web.assembler.OrderModelAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders", consumes = "application/json", produces = "application/json")
public class OrderController {

    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CollectionModel<OrderModel> getOrders(
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {

        List<Order> orders = orderService.findAll(page, size);
        return orderModelAssembler.toCollectionModel(orders, page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderModel getOrder(@PathVariable("id") Long id) {
        return orderModelAssembler.toModel(orderService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderModel createOrder(@RequestBody @Valid OrderDTO dto, Authentication authentication) {

        Order createdOrder = orderService.create(dto, authentication);
        return orderModelAssembler.toModel(createdOrder);
    }

    @GetMapping("/{userId}/user-orders")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public CollectionModel<OrderModel> getOrdersByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size,
            Authentication authentication) {

        List<Order> userOrders = orderService.findOrdersInfoByUserId(userId, page, size, authentication);
        return orderModelAssembler.toCollectionModel(userOrders, page, size);
    }

}