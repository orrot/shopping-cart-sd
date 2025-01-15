package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.common.specification.BusinessRuleResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SupportedPaymentMethodRuleTest {

    private static final String DEFAULT_PAYMENT_CODE = "VISA";
    private static final PaymentMethod DEFAULT_PAYMENT = PaymentMethod.builder()
            .code(DEFAULT_PAYMENT_CODE)
            .build();

    @InjectMocks
    private SupportedPaymentMethodRule supportedPaymentMethodRule;

    @Mock
    private PaymentMethodOutputPort paymentMethodOutputPort;

    @Nested
    @DisplayName("When checking if the payment method is supported")
    class WhenCheckingIfPaymentMethodIsSupported {

        @Test
        @DisplayName("Should return success if the payment method is supported")
        void shouldReturnSuccessIfPaymentMethodIsSupported() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withPaymentMethod(DEFAULT_PAYMENT);
            given(paymentMethodOutputPort.existsById(DEFAULT_PAYMENT_CODE)).willReturn(true);

            // When
            BusinessRuleResult result = supportedPaymentMethodRule.isSatisfiedBy(cart);

            // Then
            assertThat(result.isRuleSatisfied()).isTrue();
        }

        @Test
        @DisplayName("Should return error if the payment method is not supported")
        void shouldReturnErrorIfPaymentMethodIsNotSupported() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withPaymentMethod(DEFAULT_PAYMENT);
            given(paymentMethodOutputPort.existsById(DEFAULT_PAYMENT_CODE)).willReturn(false);

            // When
            BusinessRuleResult result = supportedPaymentMethodRule.isSatisfiedBy(cart);

            // Then
            assertThat(result.isRuleSatisfied()).isFalse();
            assertThat(result.notifications())
                    .containsExactlyInAnyOrder("The Payment Method 'VISA' is not supported. Please use a valid one.");
        }
    }
}