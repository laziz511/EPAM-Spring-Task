package com.epam.esm.web.assembler;

import com.epam.esm.core.entity.Order;
import com.epam.esm.core.model.OrderModel;
import com.epam.esm.web.controller.GiftCertificateController;
import com.epam.esm.web.controller.OrderController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.controller.UserController;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler extends BaseAssembler<Order, OrderModel> {
    public OrderModelAssembler() {
        super(OrderController.class, OrderModel.class);
    }

    @Override
    @SneakyThrows
    public OrderModel toModel(@Nullable Order entity) {
        OrderModel orderModel = instantiateModel(Objects.requireNonNull(entity));

        orderModel.add(createSelfLink(entity.getId()),
                createPostLink(),
                createUserLink(entity.getUser().getId()),
                createGiftCertificateLink(entity.getGiftCertificate().getId()));

        setModelAttributes(orderModel, entity);
        return orderModel;
    }

    @SneakyThrows
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities, int page, int size) {
        CollectionModel<OrderModel> orderModels = super.toCollectionModel(entities);

        orderModels.add(linkTo(methodOn(TagController.class).getTags(page, size)).withSelfRel());

        return orderModels;
    }

    private Link createUserLink(Long userId) {
        return linkTo(methodOn(UserController.class).getUser(userId))
                .withRel("user")
                .withType(String.valueOf(HttpMethod.GET));
    }

    private Link createGiftCertificateLink(Long giftCertificateId) {
        return linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(giftCertificateId))
                .withRel("giftCertificate")
                .withType(String.valueOf(HttpMethod.GET));
    }

    private void setModelAttributes(OrderModel orderModel, Order entity) {
        orderModel.setId(entity.getId());
        orderModel.setPrice(entity.getPrice());
        orderModel.setOrderedTime(entity.getOrderedTime());
        orderModel.setUserId(entity.getUser().getId());
        orderModel.setGiftCertificateId(entity.getGiftCertificate().getId());
    }
}