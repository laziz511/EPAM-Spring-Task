package com.epam.esm.web.assembler;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.model.TagModel;
import com.epam.esm.web.controller.TagController;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler extends BaseAssembler<Tag, TagModel> {
    public TagModelAssembler() {
        super(TagController.class, TagModel.class);
    }

    @Override
    @SneakyThrows
    public TagModel toModel(@Nullable Tag entity) {
        TagModel tagModel = instantiateModel(Objects.requireNonNull(entity));

        tagModel.add(createSelfLink(entity.getId()),
                createToAllLink(),
                createPostLink(),
                createDeleteLink(entity.getId()));

        tagModel.setId(entity.getId());
        tagModel.setName(entity.getName());

        return tagModel;
    }

    @SneakyThrows
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends Tag> entities, int page, int size) {
        CollectionModel<TagModel> tagModels = super.toCollectionModel(entities);

        tagModels.add(linkTo(methodOn(TagController.class).getTags(page, size)).withSelfRel());

        return tagModels;
    }
}