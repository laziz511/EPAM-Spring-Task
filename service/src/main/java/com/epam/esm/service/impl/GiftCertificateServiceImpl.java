package com.epam.esm.service.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.GiftCertificateTag;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.GiftCertificateNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateTagRepository giftCertificateTagRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        giftCertificate.setCreateDate(currentDateTime);
        giftCertificate.setLastUpdateDate(currentDateTime);

        GiftCertificate createdCertificate = giftCertificateRepository.save(giftCertificate);

        List<Tag> tags = giftCertificate.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(tags);

            createdCertificate.setTags(tags);
            saveTagAssociations(createdCertificate, tags);
        }
        return createdCertificate;
    }

    @Override
    public GiftCertificate findGiftCertificateById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id)
                .map(this::populateTags);

        if (giftCertificate.isPresent())
            return giftCertificate.get();
        else
            throw new GiftCertificateNotFoundException("Not found");
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificates() {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll();
        certificates.forEach(this::populateTags);
        return certificates;
    }

    @Override
    public GiftCertificate updateGiftCertificate(Long id, GiftCertificate updatedGiftCertificate) {
        GiftCertificate existingGiftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new GiftCertificateNotFoundException("Gift certificate not found with ID: " + id));

        updateFields(existingGiftCertificate, updatedGiftCertificate);

        List<Tag> updatedTags = updatedGiftCertificate.getTags();
        if (!CollectionUtils.isEmpty(updatedTags)) {
            updateTags(updatedTags);

            existingGiftCertificate.setTags(updatedTags);
            saveTagAssociations(existingGiftCertificate, updatedTags);
        }

        giftCertificateRepository.update(existingGiftCertificate);
        return existingGiftCertificate;
    }

    @Override
    public void deleteGiftCertificate(Long id) {
        giftCertificateRepository.delete(id);
    }

    @Override
    public List<GiftCertificate> findCertificatesByCriteria(String tagName, String search, String sortBy, boolean ascending) {
        List<GiftCertificate> certificatesByCriteria = giftCertificateRepository.findCertificatesByCriteria(tagName, search, sortBy, ascending);

        certificatesByCriteria.forEach(this::populateTags);
        return certificatesByCriteria;
    }

    private GiftCertificate populateTags(GiftCertificate certificate) {
        List<GiftCertificateTag> associations = giftCertificateTagRepository.findAssociationsByGiftCertificateId(certificate.getId());

        List<Tag> tags = associations.stream()
                .map(association -> tagService.findTagById(association.getTagId()))
                .collect(Collectors.toList());

        certificate.setTags(tags);
        return certificate;
    }

    private void saveTagAssociations(GiftCertificate createdCertificate, List<Tag> tags) {
        for (Tag tag : tags) {
            GiftCertificateTag association = new GiftCertificateTag();
            association.setGiftCertificateId(createdCertificate.getId());
            association.setTagId(tag.getId());
            giftCertificateTagRepository.save(association);
        }
    }

    private void updateTags(List<Tag> updatedTags) {
        for (Tag tag : updatedTags) {
            Optional<Tag> existingTag = tagService.findTagByName(tag.getName());
            Tag savedTag = existingTag.orElseGet(() -> tagService.createTag(tag));
            tag.setId(savedTag.getId());
        }
    }

    private static void updateFields(GiftCertificate existingGiftCertificate, GiftCertificate updatedGiftCertificate) {
        if (updatedGiftCertificate.getName() != null) {
            existingGiftCertificate.setName(updatedGiftCertificate.getName());
        }

        if (updatedGiftCertificate.getDescription() != null) {
            existingGiftCertificate.setDescription(updatedGiftCertificate.getDescription());
        }

        if (updatedGiftCertificate.getPrice() != null) {
            existingGiftCertificate.setPrice(updatedGiftCertificate.getPrice());
        }

        if (updatedGiftCertificate.getDuration() != 0) {
            existingGiftCertificate.setDuration(updatedGiftCertificate.getDuration());
        }

        existingGiftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

}
