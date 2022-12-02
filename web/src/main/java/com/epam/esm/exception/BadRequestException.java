package com.epam.esm.exception;

/**
 * The type Bad request exception.
 */
public class BadRequestException extends RuntimeException {
    private final Class<?> resourceClass;

    /**
     * Instantiates a new Bad request exception.
     *
     * @param resourceClass the resource class
     */
    public BadRequestException(Class<?> resourceClass) {
        super();
        this.resourceClass = resourceClass;
    }

    /**
     * Gets resource class.
     *
     * @return the resource class
     */
    public Class<?> getResourceClass() {
        return resourceClass;
    }
}