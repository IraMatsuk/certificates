package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    @Value("5")
    private int maxResultAmount;
    private int lastPage;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, @Qualifier("tagServiceMapper") TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public Set<TagDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<Tag> tagSet = tagRepository.findAll(pageable).toSet();
        lastPage = tagRepository.findAll(pageable).getTotalPages();
        return tagSet.stream()
                .map(tagMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<TagDto> findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            TagDto tagDto = tagMapper.mapToDto(tag.get());
            return Optional.of(tagDto);
        } else {
            return Optional.empty();
        }
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
    public Optional<TagDto> findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);
        if (tag.isPresent()) {
            TagDto tagDto = tagMapper.mapToDto(tag.get());
            return Optional.of(tagDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}
