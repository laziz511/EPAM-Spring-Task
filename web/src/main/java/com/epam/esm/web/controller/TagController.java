package com.epam.esm.web.controller;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TagController is responsible for handling HTTP requests related to tags.
 * It provides endpoints for creating, retrieving, and deleting tags.
 * <p>
 * The class includes the following endpoints:
 * - POST `/tags`: Creates a new tag.
 * - GET `/tags/{id}`: Retrieves a tag by its ID.
 * - GET `/tags`: Retrieves all tags.
 * - DELETE `/tags/{id}`: Deletes a tag by its ID.
 * <p>
 * The controller uses the {@link com.epam.esm.core.entity.Tag} entity and communicates with the
 * {@link com.epam.esm.service.TagService} for handling business logic related to tags.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tags",
        consumes = {"application/json"},
        produces = {"application/json"})
public class TagController {

    /**
     * The service responsible for handling business logic related to tags.
     */
    private final TagService tagService;

    /**
     * Endpoint for creating a new tag.
     *
     * @param tag The tag to be created.
     * @return ResponseEntity containing the created tag and HTTP status code 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.create(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    /**
     * Endpoint for retrieving a tag by its ID.
     *
     * @param id The ID of the tag to be retrieved.
     * @return ResponseEntity containing the retrieved tag and HTTP status code 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> findTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        return ResponseEntity.ok(tag);
    }

    /**
     * Endpoint for retrieving all tags.
     *
     * @return ResponseEntity containing the list of all tags and HTTP status code 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Tag>> findAllTags() {
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }

    /**
     * Endpoint for deleting a tag by its ID.
     *
     * @param id The ID of the tag to be deleted.
     * @return ResponseEntity with HTTP status code 204 (No Content) indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
