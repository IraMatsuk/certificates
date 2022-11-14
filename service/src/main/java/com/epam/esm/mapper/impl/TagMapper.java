package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service("tagServiceMapper")
public class TagMapper implements Mapper<Tag, TagDto> {
    @Override
    public TagDto mapToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    @Override
    public Tag mapToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        var id = tagDto.getId();
        if (id != null) {
            tag.setId(id);
        }
        tag.setName(tagDto.getName());
        return tag;
    }
}
