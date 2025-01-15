package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.UpdateCartInfoUseCase;
import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.common.podam.MockerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UpdateCartInfoUseCaseTest {

    private static final Long DEFAULT_CART_ID = 1L;

    @InjectMocks
    private UpdateCartInfoUseCase updateCartInfoUseCase;

    @Mock
    private CartService cartService;

    @Captor
    private ArgumentCaptor<Cart> captorCart;

    @Nested
    @DisplayName("When updating cart info")
    class WhenUpdatingCartInfo {

        @Test
        @DisplayName("Should update payment method and owner")
        void shouldUpdatePaymentMethodAndOwner() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);

            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            // When
            updateCartInfoUseCase.updateCartInfo(
                    DEFAULT_CART_ID, "OTHER PAYMENT", 30L);

            // Then
            then(cartService).should()
                    .updateCart(captorCart.capture());
            var cartUpdated = captorCart.getValue();
            assertThat(cartUpdated)
                    .isNotNull()
                    .as("Payment and owner should be updated")
                    .extracting(Cart::getPaymentMethodCode, Cart::getOnlineClientOwnerId, Cart::isOnlineClientAssigned)
                    .contains("OTHER PAYMENT", 30L, true);
        }

        @Test
        @DisplayName("Should update only payment method if owner is null")
        void shouldUpdateOnlyPaymentMethodIfOwnerIsNull() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);

            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            // When
            updateCartInfoUseCase.updateCartInfo(
                    DEFAULT_CART_ID, "OTHER PAYMENT", null);

            // Then
            then(cartService).should()
                    .updateCart(captorCart.capture());
            var cartUpdated = captorCart.getValue();
            assertThat(cartUpdated)
                    .isNotNull()
                    .as("Only payment method should be updated")
                    .extracting(Cart::getPaymentMethodCode, Cart::getOnlineClientOwnerId, Cart::isOnlineClientAssigned)
                    .contains("OTHER PAYMENT", cart.getOnlineClientOwnerId(), true);
        }

        @Test
        @DisplayName("Should update only owner if payment method is null")
        void shouldUpdateOnlyOwnerIfPaymentMethodIsNull() {
            // Given
            Cart cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);

            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            // When
            updateCartInfoUseCase.updateCartInfo(
                    DEFAULT_CART_ID, null, 30L);

            // Then
            then(cartService).should()
                    .updateCart(captorCart.capture());
            var cartUpdated = captorCart.getValue();
            assertThat(cartUpdated)
                    .isNotNull()
                    .as("Only client owner id should be updated")
                    .extracting(Cart::getPaymentMethodCode, Cart::getOnlineClientOwnerId, Cart::isOnlineClientAssigned)
                    .contains(cart.getPaymentMethodCode(), 30L, true);
        }

        @Test
        @DisplayName("Should throw exception if cart does not exist")
        void shouldThrowExceptionIfCartDoesNotExist() {
            // Given
            given(cartService.findById(DEFAULT_CART_ID)).willReturn(Optional.empty());

            // Then
            assertThatThrownBy(() -> updateCartInfoUseCase
                    .updateCartInfo(DEFAULT_CART_ID, "ANY", 100L))
                    .isInstanceOf(UnExistingResourceException.class)
                    .hasMessageContaining("Cart ID '%d' must exist to be updated", DEFAULT_CART_ID);
        }
    }
}
