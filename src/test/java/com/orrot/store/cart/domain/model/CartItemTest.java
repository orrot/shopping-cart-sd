package com.orrot.store.cart.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CartItemTest {

    @Nested
    @DisplayName("When creating a CartItem")
    class WhenCreateCartItem {

        @Test
        @DisplayName("Should create a Cart Item with valid data")
        void shouldCreateCartItemWithValidData() {
            CartItem cartItem = CartItem.of(1L, "Product Name", BigDecimal.valueOf(100), 2);

            assertThat(cartItem).isNotNull();
            assertThat(cartItem.getProductId()).isEqualTo(1L);
            assertThat(cartItem.getProductName()).isEqualTo("Product Name");
            assertThat(cartItem.getCurrentPrice()).isEqualTo(BigDecimal.valueOf(100));
            assertThat(cartItem.getQuantity()).isEqualTo(2);
        }

        @Test
        @DisplayName("Should create a Cart Item with only required fields")
        void shouldCreateCartItemWithOnlyRequiredFields() {
            CartItem cartItem = CartItem.of(1L, BigDecimal.TEN, 2);

            assertThat(cartItem).isNotNull();
            assertThat(cartItem.getProductId()).isEqualTo(1L);
            assertThat(cartItem.getCurrentPrice()).isEqualTo(BigDecimal.TEN);
            assertThat(cartItem.getQuantity()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("When calculating the subtotal")
    class WhenCalculateSubtotal {

        @Test
        @DisplayName("Should return the correct subtotal")
        void shouldCalculateSubtotalCorrectly() {
            CartItem cartItem = CartItem.of(1L, BigDecimal.valueOf(100), 2);

            assertThat(cartItem.getSubtotal())
                    .as("Subtotal should be the price multiplied by the quantity")
                    .isEqualByComparingTo(BigDecimal.valueOf(200));
        }

        @Test
        @DisplayName("Should return zero when the price is null")
        void shouldReturnZeroWhenThePriceIsNull() {
            CartItem cartItem = CartItem.of(1L, null, 2);

            assertThat(cartItem.getSubtotal())
                    .as("Subtotal should be the price multiplied by the quantity")
                    .isEqualByComparingTo(BigDecimal.ZERO);
        }
    }
}