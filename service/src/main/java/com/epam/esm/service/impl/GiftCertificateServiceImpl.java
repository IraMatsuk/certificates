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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    @Value("5")
    private int maxResultAmount;
    private int lastPage;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      @Qualifier("certificateServiceMapper") GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public Set<GiftCertificateDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(pageable).toSet();
        lastPage = giftCertificateRepository.findAll(pageable).getTotalPages();
        return giftCertificates.stream()
                .map(giftCertificateMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<GiftCertificateDto> findById(Long id) {
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
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = giftCertificateMapper.mapToEntity(giftCertificateDto);
        certificate.getTags().forEach(t -> {
            Optional<Tag> tag = tagRepository.findByName(t.getName());
            tag.ifPresent(value -> t.setId(value.getId()));
        });
        GiftCertificate createdCertificate = giftCertificateRepository.save(certificate);
        return giftCertificateMapper.mapToDto(createdCertificate);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> updatedCertificate = giftCertificateRepository.findById(giftCertificateDto.getId());
        if (!updatedCertificate.isPresent()) {
            throw new IllegalArgumentException("Not valid"); // TODO
        }
        updateData(updatedCertificate.get(), giftCertificateDto);
        return updatedCertificate.get();
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        boolean exists = giftCertificateRepository.existsById(id);
        if (exists) {
            giftCertificateRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }

    private void updateData(GiftCertificate giftCertificate, GiftCertificateDto certificateDto) {
        if (certificateDto.getName() != null && !giftCertificate.getName().equals(certificateDto.getName())) {
            giftCertificate.setName(certificateDto.getName());
        }
        if (certificateDto.getDescription() != null && !giftCertificate.getDescription().equals(certificateDto.getDescription())) {
            giftCertificate.setDescription(certificateDto.getDescription());
        }
        if (certificateDto.getPrice() != null && !giftCertificate.getPrice().equals(certificateDto.getPrice())) {
            giftCertificate.setPrice(certificateDto.getPrice());
        }
        if (certificateDto.getDuration() > 0 && giftCertificate.getDuration() != certificateDto.getDuration()) {
            giftCertificate.setDuration(certificateDto.getDuration());
        }
    }
}