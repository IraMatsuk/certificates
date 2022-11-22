package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.util.ParameterName.ID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    @Value("5")
    private int maxResultAmount;
    private int lastPage;

    public OrderServiceImpl(OrderRepository orderRepository,
                            GiftCertificateRepository giftCertificateRepository,
                            UserRepository userRepository,
                            @Qualifier("orderServiceMapper") OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        Order order = new Order();

        Optional<User> user = userRepository.findById(orderDto.getUserId());
        if (!user.isPresent()) {
            throw new NoDataFoundException(ID, orderDto.getUserId(), OrderDto.class);
        }

        Optional<GiftCertificate> certificate = giftCertificateRepository.findById(orderDto.getCertificateId());
        if (!certificate.isPresent()) {
            throw new NoDataFoundException(ID, orderDto.getCertificateId(), OrderDto.class);
        }

        order.setUser(user.get());
        order.setCertificate(certificate.get());
        order.setCost(certificate.get().getPrice());

        Order newOrder = orderRepository.save(order);
        return orderMapper.mapToDto(newOrder);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }

    @Override
    public List<OrderDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<Order> orders = orderRepository.findAll(pageable).toSet();
        lastPage = orderRepository.findAll(pageable).getTotalPages();
        return orders.stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            OrderDto orderDto = orderMapper.mapToDto(order.get());
            return Optional.of(orderDto);
        } else {
            return Optional.empty();
        }
    }
}
