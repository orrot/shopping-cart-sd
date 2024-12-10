package com.orrot.store.shoppingcart.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
    * Cart class represents a shopping cart.
    * Only deferred validations with spring implemented. Code validations fail fast, but we need for them: more code, no validation groups, no accumulated errors in an easy way.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class Cart implements Serializable {

    @Serial
    private static final long serialVersionUID = -5512687501901452445L;

    @Setter
    private Long id;

    @Setter
    @NonNull
    // messages should be treated with i18n. For this example, we are not handling it.
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @Setter
    private Long associatedUserId;

    @Builder.Default
    private List<@Valid CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return List.copyOf(cartItems);
    }

    // refactor this to

    public void addCartItem(Product product, int quantity) {
        addCartItem(product.getId(), product.getName(), product.getPrice(), quantity);
    }

    public void addCartItem(Long productId, int quantity) {
        addCartItem(productId, null, null, quantity);
    }

    public void addCartItem(Long productId, String productName, BigDecimal price, int quantity) {

        var cartItem = CartItem.builder()
                .productId(productId)
                .productName(productName)
                .currentPrice(price)
                .quantity(quantity)
                .build();
        cartItems.add(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public BigDecimal getTotal() {

        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalWithFee(String paymentMethod, BigDecimal total) {
        return switch (paymentMethod) {
            case "Visa":
                yield total.add(total.multiply(new BigDecimal("0.02"))).add(new BigDecimal(800));
            case "MasterCard":
                yield total.add(total.multiply(new BigDecimal("0.04"))).add(new BigDecimal(800));
            case "Cash":
                yield total;
            default:
                throw new IllegalArgumentException("Payment method is not supported");
        };
    }

}
