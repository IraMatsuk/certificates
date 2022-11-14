package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    GiftCertificate update(GiftCertificateDto giftCertificateDto);
}
