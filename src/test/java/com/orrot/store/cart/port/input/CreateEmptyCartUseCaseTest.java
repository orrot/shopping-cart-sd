package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.CreateEmptyCartUseCase;
import com.orrot.store.common.podam.MockerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CreateEmptyCartUseCaseTest {

    private static final Long DEFAULT_CART_ID = 1L;

    @InjectMocks
    private CreateEmptyCartUseCase createEmptyCartUseCase;

    @Mock
    private CartService cartService;

    @Nested
    @DisplayName("When creating an empty cart")
    class WhenCreatingEmptyCart {

        @Test
        @DisplayName("Should create and return an empty cart with a valid ID")
        void shouldCreateAndReturnEmptyCart() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(null);
            given(cartService.createEmptyCart(cart))
                    .willReturn(cart.withId(DEFAULT_CART_ID));

            // When
            var createdCart = createEmptyCartUseCase.createEmptyCart(cart);

            // Then
            assertThat(createdCart)
                    .isNotNull()
                    .as("Cart ID should be '%d', because was generated by the service", DEFAULT_CART_ID)
                    .extracting(Cart::getId)
                    .isEqualTo(DEFAULT_CART_ID);
        }
    }
}