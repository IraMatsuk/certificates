package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private int maxResultAmount;
    private int lastPage;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      @Qualifier("certificateServiceMapper") GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateMapper = giftCertificateMapper;
    }

//    @Override
//    public Set<GiftCertificateDto> findAll(int page) {
//        Pageable pageable = PageRequest.of(page, maxResultAmount);
//        Set<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(pageable).toSet();
//        lastPage = giftCertificateRepository.findAll(pageable).getTotalPages();
//        return giftCertificates.stream()
//                .map(giftCertificateMapper::mapToDto)
//                .collect(Collectors.toSet());
//    }

    @Override
    public Set<GiftCertificateDto> findAll(int page, int size) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        return giftCertificates.
                stream()
                .map(giftCertificateMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<GiftCertificateDto> findById(long id) {
        Optional<GiftCertificate> certificate = giftCertificateRepository.findById(id);
        if (certificate.isPresent()) {
            GiftCertificateDto giftCertificate = giftCertificateMapper.mapToDto(certificate.get());
            return Optional.of(giftCertificate);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdCertificate = giftCertificateMapper.mapToEntity(giftCertificateDto);
        createdCertificate.getTags().forEach(t -> {
            Optional<Tag> tag = tagRepository.findByName(t.getName());
            tag.ifPresent(value -> t.setId(value.getId()));
        });
        return giftCertificateRepository.save(createdCertificate);
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}