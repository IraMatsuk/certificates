package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<TagDto> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<TagDto> findByName(String name);

    /**
     * Find most used tag set.
     *
     * @return the set
     */
    Set<TagDto> findMostUsedTag();
}
