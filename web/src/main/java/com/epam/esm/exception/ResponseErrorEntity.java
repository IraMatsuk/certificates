package com.epam.esm.exception;

import java.util.ArrayList;
import java.util.List;

public class ResponseErrorEntity {
    private final String errorCode;
    private final String errorMessage;
    private final List<String> errors;

    public ResponseErrorEntity(long errorCode, Class<?> resourceClass, String errorMessage) {
        this.errorCode = errorCode + ResourceCode.findResourceCode(resourceClass);
        this.errorMessage = errorMessage;
        this.errors = new ArrayList<>();
    }

    public ResponseErrorEntity(long errorCode, Class<?> resourceClass, String errorMessage, String errors) {
        this.errorCode = errorCode + ResourceCode.findResourceCode(resourceClass);
        this.errorMessage = errorMessage;
        this.errors = new ArrayList<>();
        this.errors.add(errors);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getErrors() {
        return errors;
    }
}
