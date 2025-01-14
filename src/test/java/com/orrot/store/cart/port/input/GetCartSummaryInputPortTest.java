package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.common.podam.MockerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetCartSummaryInputPortTest {

    private static final Long DEFAULT_CART_ID = 1L;

    @InjectMocks
    private GetCartSummaryInputPort getCartSummaryInputPort;

    @Mock
    private CartService cartService;

    @Nested
    @DisplayName("When finding a cart by ID")
    class WhenFindingCartById {

        @Test
        @DisplayName("Should return the cart if it exists")
        void shouldReturnCartIfExists() {
            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);

            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            var cartReturned = getCartSummaryInputPort.findCartById(DEFAULT_CART_ID);

            assertThat(cartReturned)
                    .isEqualTo(cart);
        }

        @Test
        @DisplayName("Should throw exception if cart does not exist")
        void shouldThrowExceptionIfCartDoesNotExist() {
            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.empty());

            assertThatThrownBy(() -> getCartSummaryInputPort.findCartById(DEFAULT_CART_ID))
                    .isInstanceOf(UnExistingResourceException.class)
                    .hasMessageContaining("Cart ID '%d' does not exist", DEFAULT_CART_ID);
        }
    }
}