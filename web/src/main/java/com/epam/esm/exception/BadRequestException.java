package com.epam.esm.exception;

public class BadRequestException extends RuntimeException {
    private final Class<?> resourceClass;

    public BadRequestException(Class<?> resourceClass) {
        super();
        this.resourceClass = resourceClass;
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }
}