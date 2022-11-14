package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController extends AbstractController<GiftCertificateDto> {
    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<GiftCertificateDto> findAll(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        Set<GiftCertificateDto> certificates = certificateService.findAll(page, size);
        int lastPage = certificateService.getLastPage();
        if (!certificates.isEmpty()) {
            addLinksToCertificates(certificates);
            CollectionModel<GiftCertificateDto> method = methodOn(GiftCertificateController.class).findAll(page, size);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(certificates, links);
        } else {
            throw new NoDataFoundException(CERTIFICATES, GiftCertificateDto.class);
        }
    }

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


    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public GiftCertificate create(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificate newCertificate = certificateService.create(giftCertificateDto);
        if (newCertificate == null) {
            throw new BadRequestException(GiftCertificateDto.class);
        }
        return newCertificate;
    }

    private void addLinksToCertificates(Set<GiftCertificateDto> certificates) {
        certificates.forEach(c -> {
            Link selfLink = linkTo(methodOn(GiftCertificateController.class).findById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
