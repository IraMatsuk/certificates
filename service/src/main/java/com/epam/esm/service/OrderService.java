package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService extends BaseService<OrderDto>{
    /**
     * Find orders by user id list.
     *
     * @param id   the id
     * @param page the page
     * @param size the size
     * @return the list
     */
    List<OrderDto> findOrdersByUserId(Long id, int page, int size);
}
