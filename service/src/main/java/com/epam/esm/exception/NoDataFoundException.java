package com.epam.esm.exception;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.TAGS;

public class NoDataFoundException extends RuntimeException {
    private static final String DELIMITER = " = ";
    private static final String ALL_DATA_VALUE = "all";
    private final Class<?> resourceClass;
    private final String parameters;

    /**
     * Instantiates a new No data found exception.
     *
     * @param parameters    the parameters
     * @param resourceClass the resource class
     */
    public NoDataFoundException(String parameters, Class<?> resourceClass) {
        super();
        if (CERTIFICATES.equals(parameters) || TAGS.equals(parameters)) {
            this.parameters = parameters + DELIMITER + ALL_DATA_VALUE;
        } else {
            this.parameters = parameters.replaceAll(DELIMITER.trim(), DELIMITER);
        }
        this.resourceClass = resourceClass;
    }

    /**
     * Instantiates a new No data found exception.
     *
     * @param parameterType the parameter type
     * @param parameter     the parameter
     * @param resourceClass the resource class
     */
    public NoDataFoundException(String parameterType, Object parameter, Class<?> resourceClass) {
        super();
        this.parameters = parameterType + DELIMITER + parameter;
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

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public String getParameters() {
        return parameters;
    }
}