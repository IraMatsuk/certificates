package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base service.
 *
 * @param <T> the type parameter
 */
public interface BaseService<T> {

    /**
     * Find all list.
     *
     * @param page the page
     * @param size the size
     * @return the list
     */
    List<T> findAll(int page, int size);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(Long id);

    /**
     * Create t.
     *
     * @param t the t
     * @return the t
     */
    T create(T t);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(Long id);

    /**
     * Gets last page.
     *
     * @return the last page
     */
    int getLastPage();
}
