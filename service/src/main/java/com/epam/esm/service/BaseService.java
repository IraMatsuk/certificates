package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.Optional;
import java.util.Set;

public interface BaseService<T> {
    Set<T> findAll(int page);
    Optional<T> findById(long id);
    GiftCertificate create(T t);
    int getLastPage();
}
