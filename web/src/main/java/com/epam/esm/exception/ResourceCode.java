package com.epam.esm.exception;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.Arrays;

/**
 * The enum Resource code.
 */
public enum ResourceCode {
    /**
     * Default resource code.
     */
    DEFAULT(Object.class, "00"),
    /**
     * Certificate resource code.
     */
    CERTIFICATE(GiftCertificateDto.class, "01"),
    /**
     * Tag resource code.
     */
    TAG(TagDto.class, "02");

    private final Class<?> resourceClass;
    private final String code;

    ResourceCode(Class<?> resourceClass, String code) {
        this.resourceClass = resourceClass;
        this.code = code;
    }

    /**
     * Find resource code string.
     *
     * @param currentClass the current class
     * @return the string
     */
    public static String findResourceCode(Class<?> currentClass) {
        return Arrays.stream(values())
                .filter(r -> r.resourceClass.equals(currentClass))
                .map(ResourceCode::getCode)
                .findFirst()
                .orElseGet(DEFAULT::getCode);
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }
}
