package com.epam.esm.web.controller;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.model.TagModel;
import com.epam.esm.core.model.UserModel;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.web.assembler.UserModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users", consumes = "application/json", produces = "application/json")
public class UserController {

    private final UserModelAssembler userModelAssembler;
    private final TagServiceImpl tagService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public CollectionModel<UserModel> getUsers(
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {

        List<User> users = userService.findAll(page, size);
        return userModelAssembler.toCollectionModel(users, page, size);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserModel getUser(@PathVariable("id") Long id) {
        User userModel = userService.findById(id);
        return userModelAssembler.toModel(userModel);
    }

    @GetMapping("/{id}/most-used-tag")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public CollectionModel<TagModel> findMostUsedTagOfUserWithHighestOrderCost(@PathVariable("id") Long userId) {
        List<Tag> mostUsedTag = tagService.findMostUsedTagOfUserWithHighestOrderCost(userId);
        return userModelAssembler.toCollectionTagModel(mostUsedTag, userId);
    }
}