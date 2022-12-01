package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends RepresentationModel<OrderDto> {
    @Min(value = 1, groups = OnUpdateGroup.class)
    private Long id;

    @Null(groups = OnAggregationCreateGroup.class)
    @DecimalMin(value = "0.0")
    private BigDecimal cost;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @NotNull(groups = OnAggregationCreateGroup.class)
    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;

    @NotNull(groups = OnAggregationCreateGroup.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long certificateId;

    /**
     * Instantiates a new Order dto.
     */
    @Tolerate
    public OrderDto() {
    }
}
