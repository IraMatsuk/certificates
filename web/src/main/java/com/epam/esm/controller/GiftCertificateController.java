package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnSearchGroup;
import com.epam.esm.validation.OnUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Gift certificate controller.
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController extends AbstractController<GiftCertificateDto> {
    private final GiftCertificateService certificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param certificateService the certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Find all certificates collection model.
     *
     * @param page the page
     * @param size the size
     * @return the collection model
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<GiftCertificateDto> findAllCertificates(@RequestParam(value = "page") int page,
                                                                   @RequestParam(value = "size", required = false) int size) {
        List<GiftCertificateDto> certificates = certificateService.findAll(page, size);
        int lastPage = certificateService.getLastPage();
        if (!certificates.isEmpty()) {
            addLinksToCertificates(certificates);
            CollectionModel<GiftCertificateDto> method = methodOn(GiftCertificateController.class).findAllCertificates(page, size);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(certificates, links);
        } else {
            throw new NoDataFoundException(CERTIFICATES, GiftCertificateDto.class);
        }
    }

    /**
     * Find by id gift certificate dto.
     *
     * @param id the id
     * @return the gift certificate dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public GiftCertificateDto findById(@PathVariable("id") Long id) {
        Optional<GiftCertificateDto> giftCertificateDto = certificateService.findById(id);
        if (giftCertificateDto.isPresent()) {
            Link link = linkTo(methodOn(GiftCertificateController.class).findById(id)).withSelfRel();
            giftCertificateDto.get().add(link);
            return giftCertificateDto.get();
        } else {
            throw new NoDataFoundException(ID, id, GiftCertificateDto.class);
        }
    }

    /**
     * Create certificate response entity.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the response entity
     */
    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDto> createCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto newCertificate = certificateService.create(giftCertificateDto);
        if (newCertificate == null) {
            throw new BadRequestException(GiftCertificateDto.class);
        }
        return new ResponseEntity<>(newCertificate, CREATED);
    }

    /**
     * Update gift certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate
     */
    @Validated(OnUpdateGroup.class)
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public GiftCertificate update(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificate updatedCertificate = certificateService.update(giftCertificateDto);
        if (updatedCertificate == null) {
            throw new BadRequestException(GiftCertificateDto.class);
        }
        return updatedCertificate;
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        boolean isDeleted = certificateService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, GiftCertificateDto.class);
        }
    }

    /**
     * Find by tag names collection model.
     *
     * @param tags the tags
     * @return the collection model
     */
    @Validated(OnSearchGroup.class)
    @GetMapping(value = "/with_tags", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<GiftCertificateDto> findByTagNames(@RequestParam("tags") List<String> tags) {
        Set<GiftCertificateDto> certificates = certificateService.findByTagNames(tags);
        if (!certificates.isEmpty()) {
            List<Link> links = new ArrayList<>();
            certificates.forEach(c -> {
                Link selfLink = linkTo(methodOn(GiftCertificateController.class).findById(c.getId())).withSelfRel();
                c.add(selfLink);
                links.add(selfLink);
            });
            return CollectionModel.of(certificates, links);
        } else {
            throw new NoDataFoundException(CERTIFICATES, GiftCertificateDto.class);
        }
    }

    /**
     * Sort certificates collection model.
     *
     * @param page      the page
     * @param size      the size
     * @param sortTypes the sort types
     * @return the collection model
     */
    @GetMapping("/sort")
    public CollectionModel<GiftCertificateDto> sortCertificates(@RequestParam(value = "page") int page,
                                                                @RequestParam(value = "size", required = false) int size,
                                                                @RequestParam(value = "sort") List<String> sortTypes) {
        List<GiftCertificateDto> certificates = certificateService.sortCertificatesBySeveralParameters(page, size, sortTypes);
        int lastPage = certificateService.getLastPage();
        if (!certificates.isEmpty()) {
            addLinksToCertificates(certificates);
            CollectionModel<GiftCertificateDto> method = methodOn(GiftCertificateController.class)
                    .sortCertificates(page, size, sortTypes);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(certificates, links);
        } else {
            throw new NoDataFoundException(certificates.toString(), GiftCertificateDto.class);
        }
    }

    private void addLinksToCertificates(List<GiftCertificateDto> certificates) {
        certificates.forEach(c -> {
            Link selfLink = linkTo(methodOn(GiftCertificateController.class).findById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
