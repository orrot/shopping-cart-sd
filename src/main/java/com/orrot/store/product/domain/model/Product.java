package com.orrot.store.product.domain.model;

import com.orrot.store.cart.domain.exception.InvalidProductException;
import io.vavr.control.Either;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
@Setter
@With
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Product {

    public static final String ERROR_NO_VALID_PRODUCT = "Product should contain a non-null id, a non-null name and a non-null currentPrice";

    @ToString.Include
    private Long id;

    @NotEmpty(message = "Product name is required")
    private String name;

    @NotNull(message = "Product currentPrice is required")
    @Positive(message = "Unit currentPrice must be greater or equals to zero")
    private BigDecimal currentPrice;

    private String description;

    public static Product createValid(Long id, String name, BigDecimal currentPrice) {

        var product = Product.builder()
                .id(id)
                .name(name)
                .currentPrice(currentPrice)
                .build();

        return Either.right(product)
                .filterOrElse(p -> p.getId() != null, p -> new InvalidProductException("Product ID is required"))
                .filterOrElse(p -> p.getName() != null, p -> new InvalidProductException("Product name is required"))
                .filterOrElse(p -> p.getCurrentPrice() != null, p -> new InvalidProductException("Product currentPrice is required"))
                .mapLeft(throwable -> new InvalidProductException(ERROR_NO_VALID_PRODUCT))
                .getOrElseThrow(Function.identity());
    }
}
