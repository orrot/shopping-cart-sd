package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.model.CartItem;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.AddOrUpdateCartItemsUseCase;
import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
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
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AddOrUpdateCartItemsUseCaseTest {

    private static final Long DEFAULT_CART_ID = 1L;
    private static final Long DEFAULT_PRODUCT_ID = 10L;

    @InjectMocks
    private AddOrUpdateCartItemsUseCase addOrUpdateCartItemsUseCase;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Cart> captorCart;

    @Nested
    @DisplayName("When trying to add or update an item in the cart")
    class WhenTryingToAddOrUpdateItemInCart {

        @Test
        @DisplayName("Should throw exception if cart does not exist")
        void shouldThrowExceptionIfCartDoesNotExist() {
            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.empty());

            assertThatThrownBy(() -> addOrUpdateCartItemsUseCase.addOrUpdateCartItem(
                    CartItemPatch.CartOperation.ADD, DEFAULT_CART_ID, DEFAULT_PRODUCT_ID, 5))
                    .isInstanceOf(UnExistingResourceException.class)
                    .hasMessageContaining("Cart ID '%d' of the item to be added must exist", DEFAULT_CART_ID);
        }

        @Test
        @DisplayName("Should throw exception if product does not exist")
        void shouldThrowExceptionIfProductDoesNotExist() {
            // Given

            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);
            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));
            given(productService.findById(DEFAULT_PRODUCT_ID))
                    .willReturn(Optional.empty());

            // Then / Assert
            assertThatThrownBy(() -> addOrUpdateCartItemsUseCase.addOrUpdateCartItem(
                    CartItemPatch.CartOperation.ADD, DEFAULT_CART_ID, DEFAULT_PRODUCT_ID, 5))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Product ID '%d' does not exist", DEFAULT_PRODUCT_ID);
        }
    }

    // Please check CartItemOperationsTest to be a more detailed testing of the Entity behavior

    @Nested
    @DisplayName("When adding an item to the cart")
    class WhenAddingItemToCart {

        @Test
        @DisplayName("Should add item to cart")
        void shouldAddItemToCart() {

            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);
            var product = MockerFactory.createDummy(Product.class)
                    .withId(DEFAULT_PRODUCT_ID);

            // Given
            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            given(productService.findById(DEFAULT_PRODUCT_ID))
                    .willReturn(Optional.of(product));

            // Then
            addOrUpdateCartItemsUseCase.addOrUpdateCartItem(
                    CartItemPatch.CartOperation.ADD, DEFAULT_CART_ID, DEFAULT_PRODUCT_ID, 3);

            // Assert
            then(cartService).should()
                    .updateCart(captorCart.capture());
            var cartUpdated = captorCart.getValue();
            assertThat(cartUpdated.getItems())
                    .as("Cart should contain the added items")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(DEFAULT_PRODUCT_ID, 3));
        }
    }

    // Please check CartItemOperationsTest to be a more detailed testing of the Entity behavior

    @Nested
    @DisplayName("When removing an item from the cart")
    class WhenRemovingItemFromCart {


        @Test
        @DisplayName("Should remove item from cart")
        void shouldRemoveItemFromCart() {
            // Given
            var product = MockerFactory.createDummy(Product.class)
                    .withId(DEFAULT_PRODUCT_ID);

            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);
            cart.addItems(product, 3);

            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            given(productService.findById(DEFAULT_PRODUCT_ID))
                    .willReturn(Optional.of(product));

            // Then
            addOrUpdateCartItemsUseCase.addOrUpdateCartItem(
                    CartItemPatch.CartOperation.REMOVE, DEFAULT_CART_ID, DEFAULT_PRODUCT_ID, 3);

            // Assert
            then(cartService).should()
                    .updateCart(captorCart.capture());

            var cartUpdated = captorCart.getValue();
            assertThat(cartUpdated.getItems())
                    .as("Cart items should be empty")
                    .isEmpty();
        }

    }

    // Please check CartItemOperationsTest to be a more detailed testing of the Entity behavior

    @Nested
    @DisplayName("When setting fixed quantity for an item in the cart")
    class WhenSettingFixedQuantityForItemInCart {

        @Test
        @DisplayName("Should set fixed quantity for item in cart")
        void shouldSetFixedQuantityForItemInCart() {
            // Given
            var cart = MockerFactory.createDummy(Cart.class)
                    .withId(DEFAULT_CART_ID);
            var product = MockerFactory.createDummy(Product.class)
                    .withId(DEFAULT_PRODUCT_ID);

            given(cartService.findById(DEFAULT_CART_ID))
                    .willReturn(Optional.of(cart));

            given(productService.findById(DEFAULT_PRODUCT_ID))
                    .willReturn(Optional.of(product));

            // Then
            addOrUpdateCartItemsUseCase.addOrUpdateCartItem(
                    CartItemPatch.CartOperation.SET_FIXED_QUANTITY, DEFAULT_CART_ID, DEFAULT_PRODUCT_ID, 50);

            // Assert
            then(cartService).should()
                    .updateCart(captorCart.capture());
            var cartUpdated = captorCart.getValue();
            assertThat(cartUpdated.getItems())
                    .as("Cart should contain the added items")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(DEFAULT_PRODUCT_ID, 50));
        }
    }

}