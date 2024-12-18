package com.orrot.store.product.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Product {

    @NotNull(message = "Product ID is required")
    private Long id;

    @NotNull(message = "Product name is required")
    private String name;

    @Positive(message = "Unit price must be greater or equals to zero")
    private BigDecimal price;
}
