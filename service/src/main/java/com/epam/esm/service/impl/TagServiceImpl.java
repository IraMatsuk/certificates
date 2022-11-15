package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public Set<TagDto> findAll(int page) {
        return null;
    }

    @Override
    public Optional<TagDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        String tagName = tagDto.getName();
        Tag createdTag = new Tag();
        if (!findByName(tagName).isPresent()) {
            Tag tag = new Tag();
            tag.setName(tagName);
            createdTag = tagRepository.save(tag);
        }
        return tagMapper.mapToDto(createdTag);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public int getLastPage() {
        return 0;
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);
        if (tag.isPresent()) {
            TagDto tagDto = tagMapper.mapToDto(tag.get());
            return Optional.of(tagDto);
        } else {
            return Optional.empty();
        }
    }
}
