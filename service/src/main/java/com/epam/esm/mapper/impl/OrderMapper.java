package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderServiceMapper")
public class OrderMapper implements Mapper<Order, OrderDto> {
    private final GiftCertificateMapper certificateMapper;

    /**
     * Instantiates a new Order mapper.
     *
     * @param certificateMapper the certificate mapper
     */
    @Autowired
    public OrderMapper(GiftCertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Override
    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCertificateId(order.getCertificate().getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setCost(order.getCost());
        orderDto.setCreateDate(order.getCreateDate());
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        return null;
    }
}
