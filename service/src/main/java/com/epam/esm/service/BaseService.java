package com.epam.esm.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BaseService<T> {
  //  Set<T> findAll(int page);
    Set<T> findAll(int page, int size);
    Optional<T> findById(long id);
    int getLastPage();
}
