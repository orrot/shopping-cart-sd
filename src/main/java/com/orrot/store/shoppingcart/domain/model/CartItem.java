package com.orrot.store.shoppingcart.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
public class CartItem {

    @EqualsAndHashCode.Include
    @ToString.Include
    @NotNull(message = "Product ID is required")
    private Long productId;

    private String productName;

    @Positive(message = "Unit price must be greater or equals to zero")
    private BigDecimal currentPrice;

    @Min(value = 1, message = "Quantity {value} is not valid. Quantity must be greater than zero")
    @ToString.Include
    @Builder.Default
    private int quantity = 1;

    public static CartItem of(Long productId, String productName, BigDecimal currentPrice, int quantity) {
        return new CartItem(productId, productName, currentPrice, quantity);
    }

    public static CartItem of(Long productId, BigDecimal currentPrice, int quantity) {
        return of(productId, null, currentPrice, quantity);
    }

    public BigDecimal getSubtotal() {
        return Optional.ofNullable(currentPrice)
                .orElse(BigDecimal.ZERO)
                .multiply(new BigDecimal(quantity));
    }

}
