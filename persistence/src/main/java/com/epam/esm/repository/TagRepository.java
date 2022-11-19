package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Tag repository.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     *
     * @return the set
     */
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
