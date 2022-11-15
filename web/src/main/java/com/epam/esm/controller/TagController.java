package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.TAGS;
import static com.epam.esm.util.ParameterName.ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;



@RestController
@RequestMapping("/tags")
public class TagController extends AbstractController<TagDto> {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        TagDto newTag = tagService.create(tagDto);
        if (newTag == null) {
            throw new BadRequestException(TagDto.class);
        }
        return new ResponseEntity<>(newTag, CREATED);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<TagDto> findAll(@RequestParam("page") int page) {
        Set<TagDto> tags = tagService.findAll(page);
        int lastPage = tagService.getLastPage();
        if (!tags.isEmpty()) {
            addLinksToTags(tags);
            CollectionModel<TagDto> method = methodOn(TagController.class).findAll(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(tags, links);
        } else {
            throw new NoDataFoundException(TAGS, TagDto.class);
        }
    }


    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public TagDto findById(@PathVariable("id") Long id) {
        Optional<TagDto> tagDto = tagService.findById(id);
        if (tagDto.isPresent()) {
            Link link = linkTo(methodOn(TagController.class).findById(id)).withSelfRel();
            tagDto.get().add(link);
            return tagDto.get();
        } else {
            throw new NoDataFoundException(ID, id, TagDto.class);
        }
    }

    private void addLinksToTags(Set<TagDto> tags) {
        tags.forEach(t -> {
            Link selfLink = linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel();
            t.add(selfLink);
        });
    }
}
