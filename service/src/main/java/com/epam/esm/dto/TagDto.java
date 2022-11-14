package com.epam.esm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Pattern;

@Data
@Builder
@Relation(collectionRelation = "tags")
@EqualsAndHashCode
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;

    @Pattern(regexp = "[\\w\\p{Blank}A-Z]{3,50}")
    private String name;

    /**
     * Instantiates a new Tag dto.
     */
    @Tolerate
    public TagDto() {

    }
}
