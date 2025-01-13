package com.orrot.store.cart.domain.model;

import com.orrot.store.cart.domain.exception.QuantityLessThanZeroException;
import com.orrot.store.product.domain.model.Product;
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
    @NotNull(message = "Product is required")
    private Product product;

    @Positive(message = "Quantity must be greater than zero")
    @ToString.Include
    @Builder.Default
    private int quantity = 1;

    public static CartItem of(Product productToUse, int quantity) {
        if (quantity < 0) {
            throw new QuantityLessThanZeroException("Quantity must be greater than zero");
        }
        return new CartItem(productToUse, quantity);
    }

    public String getProductName() {
        return Optional.ofNullable(product)
                .map(Product::getName)
                .orElse(null);
    }

    public Long getProductId() {
        return Optional.ofNullable(product)
                .map(Product::getId)
                .orElse(null);
    }

    public BigDecimal getCurrentPrice() {
        return Optional.ofNullable(product)
                .map(Product::getCurrentPrice)
                .orElse(null);
    }

    public BigDecimal getSubtotal() {
        return Optional.ofNullable(getCurrentPrice())
                .orElse(BigDecimal.ZERO)
                .multiply(new BigDecimal(quantity));
    }

}
