package com.epam.esm.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Response error entity.
 */
public class ResponseErrorEntity {
    private final String errorCode;
    private final String errorMessage;
    private final List<String> errors;

    /**
     * Instantiates a new Response error entity.
     *
     * @param errorCode     the error code
     * @param resourceClass the resource class
     * @param errorMessage  the error message
     */
    public ResponseErrorEntity(long errorCode, Class<?> resourceClass, String errorMessage) {
        this.errorCode = errorCode + ResourceCode.findResourceCode(resourceClass);
        this.errorMessage = errorMessage;
        this.errors = new ArrayList<>();
    }

    /**
     * Instantiates a new Response error entity.
     *
     * @param errorCode     the error code
     * @param resourceClass the resource class
     * @param errorMessage  the error message
     * @param errors        the errors
     */
    public ResponseErrorEntity(long errorCode, Class<?> resourceClass, String errorMessage, String errors) {
        this.errorCode = errorCode + ResourceCode.findResourceCode(resourceClass);
        this.errorMessage = errorMessage;
        this.errors = new ArrayList<>();
        this.errors.add(errors);
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }
}
