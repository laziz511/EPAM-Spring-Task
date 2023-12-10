package com.epam.esm.service.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.TagNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) return tag.get();
        else throw new TagNotFoundException("Tag not found with the id: " + id);
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        tagRepository.delete(id);
    }
}
