package com.orrot.store.cart.domain.service.impl;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.rules.CartRule;
import com.orrot.store.cart.domain.service.rules.RegisteredClientRule;
import com.orrot.store.cart.domain.service.rules.SupportedPaymentMethodRule;
import com.orrot.store.cart.port.output.CartOutputPort;
import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.common.specification.BusinessRuleResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    private static final Long DEFAULT_CART_ID = 1L;

    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    @Mock
    private CartOutputPort cartOutputPort;

    private final RegisteredClientRule rule1 = Mockito.mock(RegisteredClientRule.class);

    @Spy
    private List<CartRule> cartRules = List.of(rule1);

    @Nested
    @DisplayName("When creating an empty cart")
    class WhenCreatingEmptyCart {

        @Test
        @DisplayName("Should create and return an empty cart")
        void shouldCreateAndReturnEmptyCart() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(null);

            given(rule1.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.SUCCESS);

            given(cartOutputPort.create(argThat(c -> c.getId() == null)))
                    .willReturn(cart.withId(DEFAULT_CART_ID));

            // When
            var returnedCart = cartServiceImpl.createEmptyCart(cart);

            // Then
            assertThat(returnedCart)
                    .isNotNull()
                    .as("The returned cart should contain the ID")
                    .extracting(Cart::getId)
                    .isEqualTo(DEFAULT_CART_ID);
        }

        @Test
        @DisplayName("Should throw exception if cart ID is not null")
        void shouldThrowExceptionIfCartIdIsNotNull() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);

            given(rule1.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.SUCCESS);

            // Then
            assertThatThrownBy(() -> cartServiceImpl.createEmptyCart(cart))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("The cart ID must be null to be created");

            then(cartOutputPort).shouldHaveNoInteractions();
        }

        @Test
        @DisplayName("Should throw exception if business rule is broken")
        void shouldThrowExceptionIfBusinessRuleIsBroken() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(null);

            given(rule1.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.withError("Test error"));

            // Then
            assertThatThrownBy(() -> cartServiceImpl.createEmptyCart(cart))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Test error");
        }

        // TODO It is better to perform this test in another class, this is out of the scope of the CartService and most related to test specification generic code
        @Test
        @DisplayName("Should return several notifications when several rules are broken")
        void shouldThrowExceptionWithSeveralMessagesIfSeveralBusinessRulesAreBroken() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class);

            var rule2 = Mockito.mock(SupportedPaymentMethodRule.class);
            var rule3 = Mockito.mock(RegisteredClientRule.class);
            var rule4 = Mockito.mock(RegisteredClientRule.class);
            var rule5 = Mockito.mock(SupportedPaymentMethodRule.class);

            given(rule1.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.withError("Test error"));
            given(rule2.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.withError("Fail rule"));
            given(rule3.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.withError("Oops"));
            given(rule4.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.SUCCESS);
            given(rule5.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.withError("Again Error"));

            ReflectionTestUtils.setField(cartServiceImpl, "cartRules", List.of(rule1, rule2, rule3, rule4, rule5));

            // Then
            assertThatThrownBy(() -> cartServiceImpl.createEmptyCart(cart))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Test error")
                    .hasMessageContaining("Fail rule")
                    .hasMessageContaining("Oops")
                    .hasMessageContaining("Again Error");
        }
    }

    @Nested
    @DisplayName("When updating a cart")
    class WhenUpdatingCart {

        @Test
        @DisplayName("Should update the cart")
        void shouldUpdateCart() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class);

            given(rule1.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.SUCCESS);

            // When
            cartServiceImpl.updateCart(cart);

            // Then
            then(cartOutputPort).should().update(cart);
        }

        @Test
        @DisplayName("Should throw exception if business rule is broken")
        void shouldThrowExceptionIfBusinessRuleIsBroken() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class);

            given(rule1.isSatisfiedBy(cart))
                    .willReturn(BusinessRuleResult.withError("Test error"));

            // Then
            assertThatThrownBy(() -> cartServiceImpl.updateCart(cart))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Test error");
        }
    }

    @Nested
    @DisplayName("When finding a cart by ID")
    class WhenFindingCartById {

        @Test
        @DisplayName("Should return the cart if it exists")
        void shouldReturnCartIfExists() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class);
            given(cartOutputPort.findById(1L)).willReturn(Optional.of(cart));

            // When
            var result = cartServiceImpl.findById(1L);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get()).isEqualTo(cart);
        }

        @Test
        @DisplayName("Should return empty if cart does not exist")
        void shouldReturnEmptyIfCartDoesNotExist() {
            // Given
            given(cartOutputPort.findById(1L)).willReturn(Optional.empty());

            // When
            var result = cartServiceImpl.findById(1L);

            // Then
            assertThat(result).isEmpty();
        }
    }
}