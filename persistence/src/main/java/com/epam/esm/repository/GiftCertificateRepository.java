package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * The interface Gift certificate repository.
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    /**
     * Find by tag names set.
     *
     * @param tagNames the tag names
     * @param size     the size
     * @return the certificate set founded by tag names
     */
    @Query(value = "SELECT * FROM gift_certificate " +
        "WHERE gift_certificate_id IN " +
        "(" +
        "SELECT tgc.gift_certificate_id " +
        "FROM tag_gift_certificate tgc " +
        "JOIN tag ON tgc.tag_id = tag.tag_id " +
        "WHERE tag.name IN :tagNames " +
        "GROUP BY tgc.gift_certificate_id " +
        "HAVING COUNT(DISTINCT tag.name) = :size )", nativeQuery = true)
    Set<GiftCertificate> findByTagNames(List<String> tagNames, int size);
}