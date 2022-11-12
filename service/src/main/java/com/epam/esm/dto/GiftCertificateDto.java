package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @Min(value = 1)
    private Long id;

    @Pattern(regexp = "[A-Za-z\\p{Alnum}]{3,30}")
    private String name;

    @Pattern(regexp = "[A-Za-z\\p{Alnum}]{3,300}")
    private String description;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @Min(value = 1)
    private int duration;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;


    private Set<@Valid TagDto> tags;

    /**
     * Instantiates a new Gift certificate dto.
     */
    @Tolerate
    public GiftCertificateDto() {
        tags = new LinkedHashSet<>();
    }
}
