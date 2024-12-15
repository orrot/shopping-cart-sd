package com.orrot.store.shoppingcart.domain.model;

import com.orrot.store.shoppingcart.domain.exception.QuantityLessThanZeroException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedMap;

/*
    * Cart class represents a shopping cart.
    * Only deferred validations with spring implemented. Code validations fail fast, but we need for them: more code, no validation groups, no accumulated errors in an easy way.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Cart implements Serializable {

    @Serial
    private static final long serialVersionUID = -5512687501901452445L;

    @Setter
    @Getter
    @ToString.Include
    private Long id;

    @Setter
    @Getter
    // messages should be treated with i18n. For this example, we are not handling it.
    @NotNull(message = "Payment method is required")
    @ToString.Include
    private PaymentMethod paymentMethod;

    @Setter
    @Getter
    @ToString.Include
    private Long associatedUserId;

    private final SequencedMap<Long, @Valid CartItem> cartItemsByProductId = new LinkedHashMap<>();

    public List<CartItem> getCartItems() {
        return List.copyOf(cartItemsByProductId.sequencedValues());
    }

    public void addOrUpdateItem(Long productIdToAdd, BigDecimal price, int quantity) {
        addOrUpdateItem(productIdToAdd, null, price, quantity);
    }

    public void addOrUpdateItem(Long productIdToAdd, String productName, BigDecimal price, int quantity) {

        if (quantity < 0) {
            throw new QuantityLessThanZeroException("Quantity must be greater or equals to '0'");
        }

        cartItemsByProductId.compute(productIdToAdd,
                (productId, existingItem) -> {
                    var cartItemToBeAdded = Optional.ofNullable(existingItem)
                            .map(item ->
                                    item.toBuilder().quantity(quantity).build())
                            .orElseGet(() ->
                                    CartItem.of(productIdToAdd, productName, price, quantity));
                    // Null in compute means that the item will be removed if required or just not added.
                    return cartItemToBeAdded.getQuantity() != 0 ?
                            cartItemToBeAdded :
                            null;
                });
    }

    public BigDecimal getTotal() {

        return cartItemsByProductId
                .values()
                .stream()
                .map(CartItem::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalWithFee() {
        return Optional.ofNullable(paymentMethod)
                .map(PaymentMethod::getFormula)
                .map(formula -> formula.apply(getTotal()))
                .orElse(BigDecimal.ZERO);
    }

}
