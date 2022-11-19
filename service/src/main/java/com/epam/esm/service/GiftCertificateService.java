package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Set;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    GiftCertificate update(GiftCertificateDto giftCertificateDto);
    Set<GiftCertificateDto> findByTagNames(List<String> tagNames);
}
