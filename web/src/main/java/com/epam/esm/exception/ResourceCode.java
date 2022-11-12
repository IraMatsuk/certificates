package com.epam.esm.exception;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.Arrays;

public enum ResourceCode {
    DEFAULT(Object.class, "00"),
    CERTIFICATE(GiftCertificateDto.class, "01"),
    TAG(TagDto.class, "02");

    private final Class<?> resourceClass;
    private final String code;

    ResourceCode(Class<?> resourceClass, String code) {
        this.resourceClass = resourceClass;
        this.code = code;
    }

    public static String findResourceCode(Class<?> currentClass) {
        return Arrays.stream(values())
                .filter(r -> r.resourceClass.equals(currentClass))
                .map(ResourceCode::getCode)
                .findFirst()
                .orElseGet(DEFAULT::getCode);
    }

    public String getCode() {
        return code;
    }
}
