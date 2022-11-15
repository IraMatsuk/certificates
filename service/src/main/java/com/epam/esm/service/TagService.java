package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.Optional;

public interface TagService extends BaseService<TagDto> {
    Optional<TagDto> findByName(String name);
}
