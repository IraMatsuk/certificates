package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.validation.OnCreateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserDto> {
    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create user response entity.
     *
     * @param userDto the user dto
     * @return the response entity
     */
    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.create(userDto);
        if (newUser == null) {
            throw new BadRequestException(UserDto.class);
        }
        return new ResponseEntity<>(newUser, CREATED);
    }

    /**
     * Find all users collection model.
     *
     * @param page the page
     * @param size the size
     * @return the collection model
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<UserDto> findAllUsers(@RequestParam(value = "page") int page,
                                                 @RequestParam(value = "size", required = false) int size) {
        List<UserDto> users = userService.findAll(page, size);
        int lastPage = userService.getLastPage();
        if (!users.isEmpty()) {
            addLinks(users);
            CollectionModel<UserDto> method = methodOn(UserController.class).findAllUsers(page, size);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(users, links);
        } else {
            throw new NoDataFoundException(USERS, UserDto.class);
        }
    }

    /**
     * Find by id user dto.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public UserDto findById(@PathVariable("id") Long id) {
        Optional<UserDto> userDto = userService.findById(id);
        if (userDto.isPresent()) {
            Link link = linkTo(methodOn(UserController.class).findById(id)).withSelfRel();
            userDto.get().add(link);
            return userDto.get();
        } else {
            throw new NoDataFoundException(ID, id, UserDto.class);
        }
    }

    private void addLinks(List<UserDto> users) {
        users.forEach(c -> {
            Link selfLink = linkTo(methodOn(UserController.class).findById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
