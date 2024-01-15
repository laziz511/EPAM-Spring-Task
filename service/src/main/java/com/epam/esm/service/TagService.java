package com.epam.esm.service;

import com.epam.esm.core.entity.Tag;

import java.util.List;

/**
 * The interface for services that manage Tag entities.
 */
public interface TagService extends ReadableService<Tag>, CreatableService<Tag>, DeletableService {

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders.
     *
     * @param userId The identifier of the user for whom to find the most widely used tag.
     * @return A list of Tags representing the most widely used tag(s) for the specified user with the highest order cost.
     */
    List<Tag> findMostUsedTagOfUserWithHighestOrderCost(Long userId);
}
