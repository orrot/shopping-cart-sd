package com.orrot.store.shoppingcart.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CartTotalCalculationTest {

    private final PaymentMethod VISA_PAYMENT_METHOD = PaymentMethod.builder()
            .code("VISA")
            .name("Visa")
            .fixedFee(BigDecimal.valueOf(800))
            .percentageFee(new BigDecimal("0.02"))
            .build();

    private final PaymentMethod CASH_PAYMENT_METHOD = PaymentMethod.builder()
            .code("CASH")
            .name("Cash")
            .fixedFee(BigDecimal.ZERO)
            .percentageFee(BigDecimal.ZERO)
            .build();

    private final PaymentMethod PAYMENT_METHOD_NULL_VALUES = PaymentMethod.builder()
            .code("NULL")
            .name("Null Values")
            .fixedFee(null)
            .percentageFee(null)
            .build();

    @Nested
    @DisplayName("When calculating Cart Total Price")
    class WhenCalculateCartTotal {

        @Test
        @DisplayName("Should return zero when cart is empty")
        void shouldReturnZeroIfCartIsEmpty() {
            var cart = Cart.builder()
                    .paymentMethod(CASH_PAYMENT_METHOD)
                    .build();

            // When
            var total = cart.getTotal();

            // Then
            assertThat(total)
                    .as("Total should be the sum of the items")
                    .isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Should return the sum of the total of items")
        void shouldReturnTotalOfItemsInCart() {
            // Given
            var cart = Cart.builder()
                    .paymentMethod(CASH_PAYMENT_METHOD)
                    .build();

            cart.addOrUpdateItem(1L, "Banana", new BigDecimal("2000"), 2); // 4.000
            cart.addOrUpdateItem(2L, "Orange", new BigDecimal("1000"), 3); // 3.000
            cart.addOrUpdateItem(3L, "Strawberry", new BigDecimal("2000"), 3); // 6.000

            // When
            var total = cart.getTotal();

            // Then
            assertThat(total)
                    .as("Total should be the sum of the items")
                    .isEqualByComparingTo(new BigDecimal(13_000));
        }

    }

    @Nested
    @DisplayName("When calculating Cart Total Price With Payment Fee")
    class WhenCalculateCartTotalWithFee {

        @Test
        @DisplayName("Should return the sum of the total of items with Fee for Fixed Payment Method")
        void shouldReturnSameTotalIfPaymentMethodHasNullValues() {
            // Given
            var cart = Cart.builder()
                    .paymentMethod(PAYMENT_METHOD_NULL_VALUES)
                    .build();

            cart.addOrUpdateItem(1L, "Banana", new BigDecimal("2000"), 2); // 4.000
            cart.addOrUpdateItem(2L, "Orange", new BigDecimal("1000"), 3); // 3.000
            cart.addOrUpdateItem(3L, "Strawberry", new BigDecimal("2000"), 3); // 6.000

            // When
            var totalWithFee = cart.getTotalWithFee();

            // Then
            assertThat(totalWithFee)
                    .as("Total with fee is zero because malformed payment")
                    .isEqualByComparingTo(BigDecimal.valueOf(13_000));

        }

        @Test
        @DisplayName("Should return the sum of the total of items with Fee for the Visa Payment Method")
        void shouldReturnTotalWithFeeForVisaConfiguration() {

            // Given
            var cart = Cart.builder()
                    .paymentMethod(VISA_PAYMENT_METHOD) // fee is 2% or 0.02
                    .build();

            cart.addOrUpdateItem(1L, "Banana", new BigDecimal("2000"), 2); // 4.000
            cart.addOrUpdateItem(2L, "Orange", new BigDecimal("1000"), 3); // 3.000
            cart.addOrUpdateItem(3L, "Strawberry", new BigDecimal("2000"), 3); // 6.000

            // When
            var totalWithFee = cart.getTotalWithFee();

            // Then
            assertThat(totalWithFee)
                    .as("Total should be the sum of the items plus the fee")
                    .isEqualByComparingTo(new BigDecimal(14_060));
        }

        @Test
        @DisplayName("Should return zero when cart is empty")
        void shouldReturnZeroIfCartIsEmpty() {

        }
    }
}