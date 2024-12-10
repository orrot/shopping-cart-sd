package com.orrot.store.shoppingcart.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class CartItem {

    @NonNull
    @NotNull(message = "Product ID is required")
    private Long productId;

    private String productName;

    @Positive(message = "Unit price must be greater or equals to zero")
    private BigDecimal currentPrice;

    @Min(value = 1, message = "Quantity {value} is not valid. Quantity must be greater than zero")
    @Builder.Default
    private int quantity = 1;

    public BigDecimal getSubtotal() {
        return currentPrice.multiply(new BigDecimal(quantity));
    }
}
