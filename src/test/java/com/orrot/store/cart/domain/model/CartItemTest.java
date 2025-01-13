package com.orrot.store.cart.domain.model;

import com.orrot.store.product.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CartItemTest {

    private static final Product DEFAULT_PRODUCT = Product.createValid(1L, "Product Name", BigDecimal.valueOf(100));

    @Nested
    @DisplayName("When creating a CartItem")
    class WhenCreateCartItem {

        @Test
        @DisplayName("Should create a Cart Item with valid data")
        void shouldCreateCartItemWithValidData() {
            CartItem cartItem = CartItem.of(DEFAULT_PRODUCT, 2);

            assertThat(cartItem).isNotNull();
            assertThat(cartItem.getProductId()).isEqualTo(1L);
            assertThat(cartItem.getProductName()).isEqualTo("Product Name");
            assertThat(cartItem.getCurrentPrice()).isEqualTo(BigDecimal.valueOf(100));
            assertThat(cartItem.getQuantity()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("When calculating the subtotal")
    class WhenCalculateSubtotal {

        @Test
        @DisplayName("Should return the correct subtotal")
        void shouldCalculateSubtotalCorrectly() {
            CartItem cartItem = CartItem.of(DEFAULT_PRODUCT, 2);

            assertThat(cartItem.getSubtotal())
                    .as("Subtotal should be the currentPrice multiplied by the quantity")
                    .isEqualByComparingTo(BigDecimal.valueOf(200));
        }
    }
}