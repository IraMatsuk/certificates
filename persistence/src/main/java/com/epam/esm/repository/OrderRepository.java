package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Order repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
