package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Set;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    /**
     * Update gift certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate
     */
    GiftCertificate update(GiftCertificateDto giftCertificateDto);

    /**
     * Find by tag names set.
     *
     * @param tagNames the tag names
     * @return the set
     */
    Set<GiftCertificateDto> findByTagNames(List<String> tagNames);


    /**
     * Sort certificates by several parameters list.
     *
     * @param page      the page
     * @param size      the size
     * @param sortTypes the sort types
     * @return the list
     */
    List<GiftCertificateDto> sortCertificatesBySeveralParameters(int page, int size, List<String> sortTypes);
}
