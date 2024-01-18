package com.epam.esm.web.controller;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.model.GiftCertificateModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.web.assembler.GiftCertificateModelAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/gift-certificates", consumes = "application/json", produces = "application/json")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @GetMapping
    public CollectionModel<GiftCertificateModel> getGiftCertificates(
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {

        List<GiftCertificate> giftCertificates = giftCertificateService.findAll(page, size);
        return giftCertificateModelAssembler.toCollectionModel(giftCertificates, page, size);
    }

    @GetMapping("/{id}")
    public GiftCertificateModel getGiftCertificate(@PathVariable("id") Long id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        return giftCertificateModelAssembler.toModel(giftCertificate);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateModel saveGiftCertificate(@RequestBody @Valid GiftCertificate giftCertificate) {
        GiftCertificate savedGiftCertificate = giftCertificateService.create(giftCertificate);
        return giftCertificateModelAssembler.toModel(savedGiftCertificate);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateModel updateGiftCertificate(
            @PathVariable("id") Long id,
            @RequestBody @Valid GiftCertificate updatedGiftCertificate) {
        GiftCertificate giftCertificate = giftCertificateService.update(id, updatedGiftCertificate);
        return giftCertificateModelAssembler.toModel(giftCertificate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable("id") Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public CollectionModel<GiftCertificateModel> findCertificatesByCriteria(
            @RequestParam(required = false, value = "search") String search,
            @RequestParam(required = false, value = "sortBy") String sortBy,
            @RequestParam(required = false, value = "tags") List<String> tags,
            @RequestParam(required = false, value = "ascending", defaultValue = "false") boolean ascending) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findCertificatesByCriteria(tags, search, sortBy, ascending);
        return giftCertificateModelAssembler.toCollectionModelNoPage(giftCertificates);
    }

    @PatchMapping("/{id}/duration")
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateModel updateGiftCertificateDuration(
            @PathVariable("id") Long id,
            @RequestBody @Valid Map<String, Integer> requestBody) {

        GiftCertificate giftCertificate = giftCertificateService.updateGiftCertificateDuration(id, requestBody);
        return giftCertificateModelAssembler.toModel(giftCertificate);
    }

    @PatchMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateModel updateGiftCertificatePrice(
            @PathVariable("id") Long id,
            @RequestBody @Valid Map<String, BigDecimal> requestBody) {

        GiftCertificate giftCertificate = giftCertificateService.updateGiftCertificatePrice(id, requestBody);
        return giftCertificateModelAssembler.toModel(giftCertificate);
    }
}
