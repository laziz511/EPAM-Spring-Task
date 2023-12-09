package com.epam.esm.service;

import com.epam.esm.core.entity.Tag;

import java.util.Optional;

public interface TagService extends CrudService<Tag, Long> {
    Optional<Tag> findTagByName(String name);
}

