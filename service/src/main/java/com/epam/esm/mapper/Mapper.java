package com.epam.esm.mapper;

public interface Mapper<T, D> {
    D mapToDto(T t);

    T mapToEntity(D d);
}
