package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Value("5")
    private int maxResultAmount;
    private int lastPage;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        Optional<UserDto> foundUser = findByUserName(userDto.getUsername());
        if (foundUser.isPresent()) {
            return foundUser.get();
        }

        User user = userMapper.mapToEntity(userDto);
        User newUser = userRepository.save(user);
        return userMapper.mapToDto(newUser);
    }
    
    @Override
    public Optional<UserDto> findByUserName(String name) {
        Optional<User> user = userRepository.findByUserName(name);
        if (user.isPresent()) {
            UserDto userDto = userMapper.mapToDto(user.get());
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<UserDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<User> users = userRepository.findAll(pageable).toSet();
        lastPage = userRepository.findAll(pageable).getTotalPages();
        return users.stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = userMapper.mapToDto(user.get());
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}
