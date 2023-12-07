package com.epam.esm.service;

import com.epam.esm.core.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    Tag createTag(Tag tag);

    Tag findTagById(Long id);

    Optional<Tag> findTagByName(String name);

    List<Tag> findAllTags();

    void deleteTag(Long id);
}
