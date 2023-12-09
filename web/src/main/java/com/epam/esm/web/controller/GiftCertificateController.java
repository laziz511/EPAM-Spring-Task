package com.epam.esm.web.controller;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GiftCertificateController is responsible for handling HTTP requests related to gift certificates.
 * It provides endpoints for creating, retrieving, updating, and deleting gift certificates,
 * as well as searching for gift certificates based on certain criteria.
 * <p>
 * The class includes the following endpoints:
 * - POST `/gift-certificates`: Creates a new gift certificate.
 * - GET `/gift-certificates/{id}`: Retrieves a gift certificate by its ID.
 * - GET `/gift-certificates`: Retrieves all gift certificates.
 * - PUT `/gift-certificates/{id}`: Updates a gift certificate by its ID.
 * - DELETE `/gift-certificates/{id}`: Deletes a gift certificate by its ID.
 * - GET `/gift-certificates/search`: Searches for gift certificates based on criteria such as tag name, search term,
 * sorting, and ordering.
 * <p>
 * The controller uses the {@link com.epam.esm.core.entity.GiftCertificate} entity and communicates with the
 * {@link com.epam.esm.service.GiftCertificateService} for handling business logic related to gift certificates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/gift-certificates",
        consumes = {"application/json"},
        produces = {"application/json"})
public class GiftCertificateController {

    /**
     * The service responsible for handling business logic related to gift certificates.
     */
    private final GiftCertificateService giftCertificateService;

    /**
     * Endpoint for creating a new gift certificate.
     *
     * @param giftCertificate The gift certificate to be created.
     * @return ResponseEntity containing the created gift certificate and HTTP status code 201 (Created).
     */
    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate createdCertificate = giftCertificateService.create(giftCertificate);
        return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
    }

    /**
     * Endpoint for retrieving a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate to be retrieved.
     * @return ResponseEntity containing the retrieved gift certificate and HTTP status code 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> findGiftCertificateById(@PathVariable Long id) {
        GiftCertificate certificate = giftCertificateService.findById(id);
        return ResponseEntity.ok(certificate);
    }

    /**
     * Endpoint for retrieving all gift certificates.
     *
     * @return ResponseEntity containing the list of all gift certificates and HTTP status code 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> findAllGiftCertificates() {
        List<GiftCertificate> certificates = giftCertificateService.findAll();
        return ResponseEntity.ok(certificates);
    }

    /**
     * Endpoint for updating a gift certificate by its ID.
     *
     * @param id              The ID of the gift certificate to be updated.
     * @param giftCertificate The updated gift certificate data.
     * @return ResponseEntity containing the updated gift certificate and HTTP status code 200 (OK).
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateGiftCertificate(
            @PathVariable Long id,
            @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate updatedCertificate = giftCertificateService.updateGiftCertificate(id, giftCertificate);
        return ResponseEntity.ok(updatedCertificate);
    }

    /**
     * Endpoint for deleting a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate to be deleted.
     * @return ResponseEntity with HTTP status code 204 (No Content) indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint for searching for gift certificates based on criteria.
     *
     * @param tagName   The tag name to filter gift certificates.
     * @param search    The search term to filter gift certificates.
     * @param sortBy    The field by which to sort the gift certificates.
     * @param ascending A boolean indicating whether to sort in ascending order (default is descending).
     * @return ResponseEntity containing the list of gift certificates matching the criteria and HTTP status code 200 (OK).
     */
    @GetMapping("/search")
    public ResponseEntity<List<GiftCertificate>> findCertificatesByCriteria(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending) {

        List<GiftCertificate> certificates = giftCertificateService.findCertificatesByCriteria(tagName, search, sortBy, ascending);

        return ResponseEntity.ok(certificates);
    }
}
