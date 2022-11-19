package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query(value = "SELECT t.tag_id, t.name FROM orders o " +
            "JOIN tag_gift_certificate tgc on o.gift_certificate_id = tgc.gift_certificate_id " +
            "JOIN tag t ON t.tag_id = tgc.tag_id " +
            "WHERE o.user_id = " +
            "(SELECT orders.user_id FROM orders " +
            "    GROUP BY orders.user_id " +
            "    ORDER BY SUM(orders.cost) DESC LIMIT 1) " +
            "GROUP BY t.tag_id " +
            "ORDER BY count(t.tag_id) DESC LIMIT 1", nativeQuery = true)
    Set<Tag> findMostUsedTag();
}
