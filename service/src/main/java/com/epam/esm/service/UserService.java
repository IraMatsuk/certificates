package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.Optional;

public interface UserService extends BaseService<UserDto> {
    Optional<UserDto> findByUserName(String name);
}
