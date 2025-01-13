package com.orrot.store.cart.domain.model;

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
    * Only deferred validations with spring implemented. Code validations fail fast, but we need for them: more code, we got no validation groups, we got no accumulated errors in an easy way.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Cart implements Serializable {

    public static final PaymentMethod DEFAULT_PAYMENT_METHOD = PaymentMethod.builder()
            .code("CASH")
            .build();

    @Serial
    private static final long serialVersionUID = -5512687501901452445L;

    @Setter
    @Getter
    @ToString.Include
    private Long id;

    @Getter
    // messages should be treated with i18n. For this example, we are not handling it.
    @NotNull(message = "Payment method is required")
    @ToString.Include
    @Builder.Default
    private PaymentMethod paymentMethod = DEFAULT_PAYMENT_METHOD;

    @Getter
    @ToString.Include
    private Long onlineClientOwnerId;

    private final SequencedMap<Long, @Valid CartItem> cartItemsByProductId = new LinkedHashMap<>();

    public List<CartItem> getItems() {
        return List.copyOf(cartItemsByProductId.sequencedValues());
    }

    // TODO Unit tests
    public String getPaymentMethodCode() {
        return Optional.ofNullable(paymentMethod)
                .map(PaymentMethod::getCode)
                .orElse(null);
    }

    // TODO Unit test
    public boolean isOnlineClientAssigned() {
        return onlineClientOwnerId != null;
    }

    // TODO Unit test
    public void associateCartToOwner(Long onlineClientIdToAssociate) {
        this.onlineClientOwnerId = onlineClientIdToAssociate;
    }

    // TODO Unit test
    public void updatePaymentMethod(String paymentMethodCode) {
        this.paymentMethod = PaymentMethod.builder()
                .code(paymentMethodCode)
                .build();
    }

    public void addItems(Long productIdToAdd, String productName, BigDecimal price, int quantity) {

        var newCartItem = CartItem.of(productIdToAdd, productName, price, quantity);
        cartItemsByProductId.compute(productIdToAdd,
                (productId, existingCartItem) ->
                        Optional.ofNullable(existingCartItem)
                                .map(v -> v.toBuilder().quantity(v.getQuantity() + quantity).build())
                                .orElse(newCartItem));
    }

    public void removeItems(Long productIdToRemove, int quantity) {
        var itemToRemove = CartItem.of(productIdToRemove, quantity);

        cartItemsByProductId.computeIfPresent(productIdToRemove,
                (productId, existingCartItem) -> {
                    var newQuantity = existingCartItem.getQuantity() - itemToRemove.getQuantity();
                    if (newQuantity <= 0) {
                        return null;
                    }
                    return existingCartItem.toBuilder().quantity(newQuantity).build();
                });
    }

    public void replaceItemWithFixedQuantity(Long productId, String productName, BigDecimal price, int quantity) {

        var itemToReplace = CartItem.of(productId, productName, price, quantity);
        cartItemsByProductId.put(productId, itemToReplace);
    }

    public BigDecimal getTotal() {
        return cartItemsByProductId.values()
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
