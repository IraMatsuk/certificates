package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.OrderService;
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
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserDto> {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.create(userDto);
        if (newUser == null) {
            throw new BadRequestException(UserDto.class);
        }
        return new ResponseEntity<>(newUser, CREATED);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<UserDto> findAllUsers(@RequestParam("page") int page) {
        Set<UserDto> users = userService.findAll(page);
        int lastPage = userService.getLastPage();
        if (!users.isEmpty()) {
            addLinks(users);
            CollectionModel<UserDto> method = methodOn(UserController.class).findAllUsers(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(users, links);
        } else {
            throw new NoDataFoundException(USERS, UserDto.class);
        }
    }

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


    private void addLinks(Set<UserDto> users) {
        users.forEach(c -> {
            Link selfLink = linkTo(methodOn(UserController.class).findById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
