package com.orrot.store.product.domain.model;

import com.orrot.store.cart.domain.exception.InvalidProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ProductTest {

    @Nested
    @DisplayName("When creating a Product")
    class WhenCreateCartItem {

        @Test
        @DisplayName("Should create the product if all the required fields are valid")
        void shouldCreateProductIfAllRequiredFieldsAreValid() {
            var product = Product.createValid(1L, "Product Name", BigDecimal.valueOf(100));
            assertThat(product).isNotNull();
            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isNotNull();
            assertThat(product.getCurrentPrice()).isNotNull();

        }

        @Test
        @DisplayName("Should fail if ID is null")
        void shouldFailIfIdIsNull() {
            assertThatThrownBy(() -> Product.createValid(null, "Name", BigDecimal.ONE))
                    .isInstanceOf(InvalidProductException.class);
        }

        @Test
        @DisplayName("Should fail if Name is null")
        void shouldFailIfNameIsNull() {
            assertThatThrownBy(() -> Product.createValid(1L, null, BigDecimal.ONE))
                    .isInstanceOf(InvalidProductException.class);
        }

        @Test
        @DisplayName("Should fail if current price is null")
        void shouldFailIfCurrentPriceIsNull() {
            assertThatThrownBy(() -> Product.createValid(1L, "Name", null))
                    .isInstanceOf(InvalidProductException.class);
        }
    }

}