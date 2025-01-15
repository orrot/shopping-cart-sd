package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.output.CartRepository;
import com.orrot.store.cart.port.output.CartOutputPort;
import com.orrot.store.common.exception.UnExistingResourceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CountCartItemsInputPortTest {

    private static final Long DEFAULT_CART_ID = 1L;

    @InjectMocks
    private CountCartItemsInputPort countCartItemsInputPort;

    @Mock
    private CartOutputPort cartOutputPort;

    @Nested
    @DisplayName("When counting items in the cart")
    class WhenCountingItemsInCart {

        @Test
        @DisplayName("Should return the count of items in the cart")
        void shouldReturnCountOfItemsInCart() {
            given(cartOutputPort.existsById(DEFAULT_CART_ID))
                    .willReturn(true);
            given(cartOutputPort.findSumOfItems(DEFAULT_CART_ID))
                    .willReturn(5L);

            Long count = countCartItemsInputPort.countCartItems(DEFAULT_CART_ID);

            assertThat(count).isEqualTo(5L);
        }

        @Test
        @DisplayName("Should throw exception if cart does not exist")
        void shouldThrowExceptionIfCartDoesNotExist() {
            given(cartOutputPort.existsById(DEFAULT_CART_ID)).willReturn(false);

            assertThatThrownBy(() -> countCartItemsInputPort.countCartItems(DEFAULT_CART_ID))
                    .isInstanceOf(UnExistingResourceException.class)
                    .hasMessageContaining("Cart with ID '%d' does not exist", DEFAULT_CART_ID);
        }
    }
}