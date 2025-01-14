package com.orrot.store.cart.domain.model;

import com.orrot.store.product.domain.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

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
@With
public class Cart implements Serializable {
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
    private PaymentMethod paymentMethod;

    @Getter
    @ToString.Include
    private Long onlineClientOwnerId;

    private final SequencedMap<Long, @Valid CartItem> cartItemsByProductId = new LinkedHashMap<>();

    public List<CartItem> getItems() {
        return List.copyOf(cartItemsByProductId.sequencedValues());
    }

    public String getPaymentMethodCode() {
        return Optional.ofNullable(paymentMethod)
                .map(PaymentMethod::getCode)
                .orElse(null);
    }

    public boolean isOnlineClientAssigned() {
        return onlineClientOwnerId != null;
    }

    public void associateCartToOwner(Long onlineClientIdToAssociate) {
        this.onlineClientOwnerId = onlineClientIdToAssociate;
    }

    public void updatePaymentMethod(String paymentMethodCode) {
        this.paymentMethod = PaymentMethod.builder()
                .code(paymentMethodCode)
                .build();
    }

    public void addItems(Product productToAdd, int quantity) {

        var newCartItem = CartItem.of(productToAdd, quantity);
        cartItemsByProductId.compute(productToAdd.getId(),
                (productId, existingCartItem) ->
                        Optional.ofNullable(existingCartItem)
                                .map(v -> v.toBuilder().quantity(v.getQuantity() + quantity).build())
                                .orElse(newCartItem));
    }

    public void removeItems(Product productToRemove, int quantity) {
        var itemToRemove = CartItem.of(productToRemove, quantity);

        cartItemsByProductId.computeIfPresent(productToRemove.getId(),
                (productId, existingCartItem) -> {
                    var newQuantity = existingCartItem.getQuantity() - itemToRemove.getQuantity();
                    if (newQuantity <= 0) {
                        return null;
                    }
                    return existingCartItem.toBuilder().quantity(newQuantity).build();
                });
    }

    public void replaceItemWithFixedQuantity(Product productToSet, int quantity) {

        var itemToReplace = CartItem.of(productToSet, quantity);
        cartItemsByProductId.put(productToSet.getId(), itemToReplace);
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
