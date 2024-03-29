package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static com.epam.esm.util.ParameterName.MOST_USED_TAG;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping("/tags")
public class TagController extends AbstractController<TagDto> {
    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Create tag response entity.
     *
     * @param tagDto the tag dto
     * @return the response entity
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        TagDto newTag = tagService.create(tagDto);
        if (newTag == null) {
            throw new BadRequestException(TagDto.class);
        }
        return new ResponseEntity<>(newTag, CREATED);
    }

    /**
     * Find all collection model.
     *
     * @param page the page
     * @param size the size
     * @return the collection model
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<TagDto> findAll(@RequestParam(value = "page") int page,
                                           @RequestParam(value = "size", required = false) int size) {
        List<TagDto> tags = tagService.findAll(page, size);
        int lastPage = tagService.getLastPage();
        if (!tags.isEmpty()) {
            addLinksToTags(tags);
            CollectionModel<TagDto> method = methodOn(TagController.class).findAll(page, size);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(tags, links);
        } else {
            throw new NoDataFoundException(TAGS, TagDto.class);
        }
    }


    /**
     * Find by id tag dto.
     *
     * @param id the id
     * @return the tag dto
     */
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

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        boolean isDeleted = tagService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, TagDto.class);
        }
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    @GetMapping(params = "name", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public TagDto getByName(@RequestParam @NotNull
                            @Pattern(regexp = "[\\w\\p{Blank}A-Z]{3,50}")
                                    String name) {
        Optional<TagDto> tag = tagService.findByName(name);
        if (tag.isPresent()) {
            Link link = linkTo(methodOn(TagController.class).getByName(name)).withSelfRel();
            tag.get().add(link);
            return tag.get();
        } else {
            throw new NoDataFoundException(NAME, name, TagDto.class);
        }
    }

    /**
     * Find most used tag collection model.
     *
     * @return the collection model
     */
    @GetMapping(value = "/most_used_tag", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<TagDto> findMostUsedTag() {
        Set<TagDto> tags = tagService.findMostUsedTag();
        if (!tags.isEmpty()) {
            return CollectionModel.of(tags);
        } else {
            throw new NoDataFoundException(MOST_USED_TAG, OrderDto.class);
        }
    }

    private void addLinksToTags(List<TagDto> tags) {
        tags.forEach(t -> {
            Link selfLink = linkTo(methodOn(TagController.class).findById(t.getId())).withSelfRel();
            t.add(selfLink);
        });
    }
}
