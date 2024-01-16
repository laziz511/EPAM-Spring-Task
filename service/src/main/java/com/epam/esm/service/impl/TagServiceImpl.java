package com.epam.esm.service.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.core.constants.ErrorMessageConstants.TAG_NOT_FOUND_ERROR_MESSAGE;
import static com.epam.esm.core.utils.Validator.validatePageAndSize;

/**
 * Implementation of the {@link TagService} interface.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tag> findAll(int page, int size) {
        validatePageAndSize(page, size, Tag.class);

        return tagRepository.findAll(page, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND_ERROR_MESSAGE + id, Tag.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Long id) {
        Tag tagToDelete = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND_ERROR_MESSAGE + id, Tag.class));

        tagToDelete.getGiftCertificates().forEach(giftCertificate -> giftCertificate.getTags().remove(tagToDelete));
        tagRepository.delete(tagToDelete);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tag> findMostUsedTagOfUserWithHighestOrderCost(Long userId) {
        return tagRepository.findMostUsedTagOfUserWithHighestOrderCost(userId);
    }

}