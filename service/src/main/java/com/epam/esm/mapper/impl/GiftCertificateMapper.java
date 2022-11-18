package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service("certificateServiceMapper")
public class GiftCertificateMapper implements Mapper<GiftCertificate, GiftCertificateDto> {
    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateMapper(@Qualifier("tagServiceMapper") TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public GiftCertificateDto mapToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto certificateDto = new GiftCertificateDto();
        certificateDto.setId(giftCertificate.getId());
        certificateDto.setName(giftCertificate.getName());
        certificateDto.setDescription(giftCertificate.getDescription());
        certificateDto.setDuration(giftCertificate.getDuration());
        certificateDto.setPrice(giftCertificate.getPrice());
        certificateDto.setCreateDate(giftCertificate.getCreateDate());
        certificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        Set<TagDto> tags = giftCertificate.getTags().stream()
                .map(tagMapper::mapToDto)
                .collect(Collectors.toSet());
        certificateDto.setTags(tags);
        return certificateDto;
    }

    @Override
    public GiftCertificate mapToEntity(GiftCertificateDto certificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        var id = certificateDto.getId();
        if (id != null) {
            giftCertificate.setId(id);
        }
        giftCertificate.setName(certificateDto.getName());
        giftCertificate.setDescription(certificateDto.getDescription());
        giftCertificate.setDuration(certificateDto.getDuration());
        giftCertificate.setPrice(certificateDto.getPrice());
        giftCertificate.setCreateDate(certificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(certificateDto.getLastUpdateDate());
        Set<Tag> tags = certificateDto.getTags().stream()
                .map(tagMapper::mapToEntity)
                .collect(Collectors.toSet());
        giftCertificate.setTags(tags);
        return giftCertificate;
    }
}
