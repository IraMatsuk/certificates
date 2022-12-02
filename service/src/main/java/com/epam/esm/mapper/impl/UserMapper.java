package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userServiceMapper")
public class UserMapper implements Mapper<User, UserDto> {
    private final OrderMapper orderMapper;
    /**
     * Instantiates a new User mapper.
     *
     * @param orderMapper     the order mapper
     */
    @Autowired
    public UserMapper(@Qualifier("orderServiceMapper") OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUserName());
        List<OrderDto> orders = user.getOrders().stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        User user = new User();
        var id = userDto.getId();
        if (id != null) {
            user.setId(id);
        }
        user.setUserName(userDto.getUsername());
        return user;
    }
}