package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService extends BaseService<UserDto> {
    /**
     * Find by user name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<UserDto> findByUserName(String name);
}
