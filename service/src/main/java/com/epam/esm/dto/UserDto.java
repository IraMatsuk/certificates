package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserDto extends RepresentationModel<UserDto> {
    @Min(value = 1, groups = {OnAggregationCreateGroup.class, OnUpdateGroup.class})
    @JsonView(View.UserWithoutOrders.class)
    private Long id;

    @NotNull(groups = OnCreateGroup.class)
    @Pattern(regexp = "[A-Za-z\\p{Alpha}]{1,25}", groups = OnCreateGroup.class)
    @JsonView(View.UserWithoutOrders.class)
    private String username;

    @JsonView(View.UserWithOrders.class)
    private List<@Valid OrderDto> orders;

    /**
     * Instantiates a new User dto.
     */
    @Tolerate
    public UserDto() {
        orders = new ArrayList<>();
    }

    public List<OrderDto> getOrders() {
        return orders;
    }
}

