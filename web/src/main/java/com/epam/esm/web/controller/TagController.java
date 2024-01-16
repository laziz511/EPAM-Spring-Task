package com.epam.esm.web.controller;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.web.assembler.TagModelAssembler;
import com.epam.esm.core.model.TagModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tags", consumes = "application/json", produces = "application/json")
public class TagController {

    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;

    @GetMapping
    public CollectionModel<TagModel> getTags(
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {

        List<Tag> tags = tagService.findAll(page, size);
        return tagModelAssembler.toCollectionModel(tags, page, size);
    }

    @GetMapping("/{id}")
    public TagModel getTag(@PathVariable("id") Long id) {
        Tag tagModel = tagService.findById(id);
        return tagModelAssembler.toModel(tagModel);
    }

    @PostMapping()
    public TagModel saveTag(@RequestBody @Valid Tag tag) {
        Tag savedTag = tagService.create(tag);
        return tagModelAssembler.toModel(savedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}