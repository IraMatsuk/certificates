package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.OrderService;
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
@RequestMapping("/orders")
public class OrderController extends AbstractController<OrderDto> {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto userOrder = orderService.create(orderDto);
        if (userOrder == null) {
            throw new BadRequestException(UserDto.class);
        }
        return new ResponseEntity<>(userOrder, CREATED);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<OrderDto> findAllOrders(@RequestParam("page") int page) {
        Set<OrderDto> orders = orderService.findAll(page);
        int lastPage = orderService.getLastPage();
        if (!orders.isEmpty()) {
            addLinks(orders);
            CollectionModel<OrderDto> method = methodOn(OrderController.class).findAllOrders(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(orders, links);
        } else {
            throw new NoDataFoundException(ORDERS, OrderDto.class);
        }
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public OrderDto findById(@PathVariable("id") Long id) {
        Optional<OrderDto> orderDto = orderService.findById(id);
        if (orderDto.isPresent()) {
            Link link = linkTo(methodOn(OrderController.class).findById(id)).withSelfRel();
            orderDto.get().add(link);
            return orderDto.get();
        } else {
            throw new NoDataFoundException(ID, id, OrderDto.class);
        }
    }

    private void addLinks(Set<OrderDto> orders) {
        orders.forEach(c -> {
            Link selfLink = linkTo(methodOn(OrderController.class).findById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
