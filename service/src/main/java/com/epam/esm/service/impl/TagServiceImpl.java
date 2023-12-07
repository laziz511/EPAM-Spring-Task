package com.epam.esm.service.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.TagNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag createTag(Tag tag) {
        // Check if a tag with the same name already exists
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        return existingTag.orElse(tagRepository.save(tag));
    }


    @Override
    public Tag findTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent())
            return tag.get();
        else
            throw new TagNotFoundException("Tag not found");

    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
