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

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController<OrderDto> {
    private final OrderService orderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create order response entity.
     *
     * @param orderDto the order dto
     * @return the response entity
     */
    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto userOrder = orderService.create(orderDto);
        if (userOrder == null) {
            throw new BadRequestException(UserDto.class);
        }
        return new ResponseEntity<>(userOrder, CREATED);
    }

    /**
     * Find all orders collection model.
     *
     * @param page the page
     * @param size the size
     * @return the collection model
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<OrderDto> findAllOrders(@RequestParam(value = "page") int page,
                                                   @RequestParam(value = "size", required = false) int size) {
        List<OrderDto> orders = orderService.findAll(page, size);
        int lastPage = orderService.getLastPage();
        if (!orders.isEmpty()) {
            addLinks(orders);
            CollectionModel<OrderDto> method = methodOn(OrderController.class).findAllOrders(page, size);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(orders, links);
        } else {
            throw new NoDataFoundException(ORDERS, OrderDto.class);
        }
    }

    /**
     * Find by id order dto.
     *
     * @param id the id
     * @return the order dto
     */
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

    /**
     * Find user orders collection model.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the collection model
     */
    @GetMapping(value = "user/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<OrderDto> findUserOrders(@PathVariable("id") Long userId,
                                         @RequestParam(value = "page") int page,
                                         @RequestParam(value = "size", required = false) int size) {
        List<OrderDto> orders = orderService.findOrdersByUserId(userId, page, size);
        int lastPage = orderService.getLastPage();
        if (!orders.isEmpty()) {
            addLinks(orders);
            CollectionModel<OrderDto> method = methodOn(OrderController.class).findUserOrders(userId, page, size);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(orders, links);
        } else {
            throw new NoDataFoundException(ID, userId, OrderDto.class);
        }
    }

    private void addLinks(List<OrderDto> orders) {
        orders.forEach(c -> {
            Link selfLink = linkTo(methodOn(OrderController.class).findById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
