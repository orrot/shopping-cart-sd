package com.orrot.store.shoppingcart.domain.model;

import com.orrot.store.shoppingcart.domain.exception.QuantityLessThanZeroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartAddItemsTest {

    public static final Long PRODUCT_ID_ONE = 1L;
    public static final Long PRODUCT_ID_TWO = 2L;
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.TEN;

    private final PaymentMethod FIXED_PAYMENT_METHOD = PaymentMethod.builder()
            .code("PM_FIXED")
            .build();

    @Nested
    @DisplayName("When adding an item to the cart")
    class WhenAddItem {

        @Test
        @DisplayName("Should add the items to the cart if the products are new")
        void shouldAddItemToCartIfProductIsNew() {
            // Given
            var cart = Cart.builder()
                    .paymentMethod(FIXED_PAYMENT_METHOD)
                    .build();

            // When
            cart.addOrUpdateItem(PRODUCT_ID_ONE, "ONE", DEFAULT_PRICE, 3);
            cart.addOrUpdateItem(PRODUCT_ID_TWO, "TWO", DEFAULT_PRICE, 2);

            // Then
            assertThat(cart.getCartItems())
                    .as("Cart should contain the added items")
                    .containsExactlyInAnyOrder(
                            CartItem.of(PRODUCT_ID_ONE, DEFAULT_PRICE, 3),
                            CartItem.of(PRODUCT_ID_TWO, DEFAULT_PRICE, 2));
        }

        @Test
        @DisplayName("Should sum the quantity to the product if it already exists")
        void shouldSumQuantityToExistingProduct() {
            // Given
            var cart = Cart.builder()
                    .paymentMethod(FIXED_PAYMENT_METHOD)
                    .build();

            // When
            cart.addOrUpdateItem(PRODUCT_ID_ONE, DEFAULT_PRICE, 3);
            cart.addOrUpdateItem(PRODUCT_ID_ONE, DEFAULT_PRICE, 2);

            // Then
            assertThat(cart.getCartItems())
                    .as("Cart should contain '2' items of product 'ONE'")
                    .containsExactlyInAnyOrder(
                            CartItem.of(PRODUCT_ID_ONE, DEFAULT_PRICE, 2));
        }

        @Test
        @DisplayName("Should fail if quantity is less than zero")
        void shouldFailIfQuantityIsZero() {

            // Given
            var cart = Cart.builder()
                    .paymentMethod(FIXED_PAYMENT_METHOD)
                    .build();

            // When
            assertThatThrownBy(() -> cart.addOrUpdateItem(PRODUCT_ID_ONE, DEFAULT_PRICE, -1))
                    .as("Should throw an exception when quantity is less than zero")
                    .isInstanceOf(QuantityLessThanZeroException.class)
                    .hasMessage("Quantity must be greater or equals to '0'");
        }

        @Test
        @DisplayName("Should delete the item if the quantity is zero")
        void shouldDeleteItemIfQuantityIsZero() {
            // Given
            var cart = Cart.builder()
                    .paymentMethod(FIXED_PAYMENT_METHOD)
                    .build();

            // When
            cart.addOrUpdateItem(PRODUCT_ID_ONE, DEFAULT_PRICE, 2);
            cart.addOrUpdateItem(PRODUCT_ID_TWO, DEFAULT_PRICE, 3);
            cart.addOrUpdateItem(PRODUCT_ID_ONE, DEFAULT_PRICE, 0);

            // Then
            assertThat(cart.getCartItems())
                    .as("Cart should contain ONLY Product 'TWO'")
                    .containsExactlyInAnyOrder(
                            CartItem.of(PRODUCT_ID_TWO, DEFAULT_PRICE, 3));
        }
    }

}