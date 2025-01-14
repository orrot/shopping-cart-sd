package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.adapter.output.PaymentMethodRepository;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.specification.BusinessRuleResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SupportedPaymentMethodRuleTest {

//    @InjectMocks
//    private SupportedPaymentMethodRule supportedPaymentMethodRule;
//
//    @Mock
//    private PaymentMethodRepository paymentMethodRepository;
//
//    @Nested
//    @DisplayName("When checking if the payment method is supported")
//    class WhenCheckingIfPaymentMethodIsSupported {
//
//        @Test
//        @DisplayName("Should return success if the payment method is not assigned")
//        void shouldReturnSuccessIfPaymentMethodIsNotAssigned() {
//            // Given
//            Cart cart = new Cart();
//
//            // When
//            BusinessRuleResult result = supportedPaymentMethodRule.isSatisfiedBy(cart);
//
//            // Then
//            assertThat(result.isSuccess()).isTrue();
//        }
//
//        @Test
//        @DisplayName("Should return success if the payment method is supported")
//        void shouldReturnSuccessIfPaymentMethodIsSupported() {
//            // Given
//            Cart cart = new Cart();
//            cart.setPaymentMethodCode("PM123");
//            given(paymentMethodRepository.existsById("PM123")).willReturn(true);
//
//            // When
//            BusinessRuleResult result = supportedPaymentMethodRule.isSatisfiedBy(cart);
//
//            // Then
//            assertThat(result.isSuccess()).isTrue();
//        }
//
//        @Test
//        @DisplayName("Should return error if the payment method is not supported")
//        void shouldReturnErrorIfPaymentMethodIsNotSupported() {
//            // Given
//            Cart cart = new Cart();
//            cart.setPaymentMethodCode("PM123");
//            given(paymentMethodRepository.existsById("PM123")).willReturn(false);
//
//            // When
//            BusinessRuleResult result = supportedPaymentMethodRule.isSatisfiedBy(cart);
//
//            // Then
//            assertThat(result.isSuccess()).isFalse();
//            assertThat(result.getErrorMessage()).isEqualTo("The Payment Method 'PM123' is not supported. Please use a valid one.");
//        }
//    }
}