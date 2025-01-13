package com.orrot.store.cart.domain.model;

import com.orrot.store.cart.domain.exception.QuantityLessThanZeroException;
import com.orrot.store.product.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

class CartItemOperationsTest {

    public static final Long PRODUCT_ID_ONE = 1L;
    public static final Long PRODUCT_ID_TWO = 2L;
    public static final String DEFAULT_PRODUCT_NAME = "DEFAULT_PRODUCT";
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.TEN;

    private final PaymentMethod FIXED_PAYMENT_METHOD = PaymentMethod.builder()
            .code("PM_FIXED")
            .build();

    @Nested
    @DisplayName("When Adding Cart Items")
    class WhenAddingCartItems {

        @Test
        @DisplayName("Should add the items to the cart if the products are new")
        void shouldAddItemsToCartIfProductIsNew() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 3);
            cart.addItems(createProductWithId(PRODUCT_ID_TWO), 2);

            // Then
            assertThat(cart.getItems())
                    .as("Cart should contain the added items")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(PRODUCT_ID_ONE, 3),
                            tuple(PRODUCT_ID_TWO, 2));
        }

        @Test
        @DisplayName("Should sum the quantity to the product if it already exists")
        void shouldSumQuantityToExistingProduct() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 3);
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 2);

            // Then
            assertThat(cart.getItems())
                    .as("Cart should contain '5' items of product 'ONE'")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(PRODUCT_ID_ONE, 5));
        }

        @Test
        @DisplayName("Should fail if quantity is less than zero")
        void shouldFailIfQuantityIsLessThanZero() {

            // Given
            var cart = createEmptyCart();

            // When
            assertThatThrownBy(() -> cart.addItems(createProductWithId(PRODUCT_ID_ONE), -1))
                    .as("Should throw an exception when quantity is less than zero")
                    .isInstanceOf(QuantityLessThanZeroException.class)
                    .hasMessage("Quantity must be greater than zero");
        }
    }

    @Nested
    @DisplayName("When Deleting Cart Items")
    class WhenDeleteItem {

        @Test
        @DisplayName("Should not fail and do nothing if removing product that does not exist")
        void shouldDoNothingIfRemoveProductThatDoesNotExist() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 5);
            cart.removeItems(createProductWithId(PRODUCT_ID_TWO), 3);
            // Then
            assertThat(cart.getItems())
                    .as("Cart should contain ONLY '2' of Product 'TWO'")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(PRODUCT_ID_ONE, 5));
        }

        @Test
        @DisplayName("Should subtract the quantity and keep the rest of the items")
        void shouldSubstractQuantityAndKeepTheRest() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 5);
            cart.removeItems(createProductWithId(PRODUCT_ID_ONE), 3);
            // Then
            assertThat(cart.getItems())
                    .as("Cart should contain ONLY '2' of Product 'TWO'")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(PRODUCT_ID_ONE, 2));
        }

        @Test
        @DisplayName("Should remove entirely the product from the cart if the item substracted is bigger than existing")
        void shouldRemoveEntirelyTheItemIfQuantityIsGreaterThanCurrentItems() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 5);
            cart.removeItems(createProductWithId(PRODUCT_ID_ONE), 6);
            // Then
            assertThat(cart.getItems())
                    .as("Cart should NOT contain Products")
                    .isEmpty();
        }

        @Test
        @DisplayName("Should remove entirely the product from the cart if the item substracted is equal than existing")
        void shouldRemoveEntirelyTheItemIfQuantityIsEqualThanCurrentItems() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 5);
            cart.removeItems(createProductWithId(PRODUCT_ID_ONE), 5);
            // Then
            assertThat(cart.getItems())
                    .as("Cart should NOT contain Products")
                    .isEmpty();
        }


        @Test
        @DisplayName("Should fail if quantity is less than zero")
        void shouldFailIfQuantityIsLessThanZero() {

            // Given
            var cart = createEmptyCart();

            // When
            assertThatThrownBy(() -> cart.removeItems(createProductWithId(PRODUCT_ID_ONE), -1))
                    .as("Should throw an exception when quantity is less than zero")
                    .isInstanceOf(QuantityLessThanZeroException.class)
                    .hasMessage("Quantity must be greater than zero");
        }

    }

    private Cart createEmptyCart() {
        return Cart.builder()
                .paymentMethod(FIXED_PAYMENT_METHOD)
                .build();
    }

    @Nested
    @DisplayName("When Setting arbitrary quantities of items")
    class WhenSettingArbitraryQuantity {

        @Test
        @DisplayName("Should set an arbitrary quantity in an existing item")
        void shouldSetAnArbitraryQuantityInAnExistingItem() {

            // Given
            var cart = createEmptyCart();

            // When
            cart.addItems(createProductWithId(PRODUCT_ID_ONE), 1);
            cart.replaceItemWithFixedQuantity(createProductWithId(PRODUCT_ID_ONE), 6);

            // Then
            assertThat(cart.getItems())
                    .as("Cart should NOT contain Products")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(PRODUCT_ID_ONE, 6));
        }

        @Test
        @DisplayName("Should set an arbitrary quantity in a new item")
        void shouldSetArbitraryQuantityForNewItem() {
            // Given
            var cart = createEmptyCart();

            // When
            cart.replaceItemWithFixedQuantity(createProductWithId(PRODUCT_ID_ONE), 6);

            // Then
            assertThat(cart.getItems())
                    .as("Cart should NOT contain Products")
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(PRODUCT_ID_ONE, 6));
        }

        @Test
        @DisplayName("Should fail if quantity is less than zero")
        void shouldFailIfQuantityIsLessThanZero() {

            // Given
            var cart = createEmptyCart();

            // When
            assertThatThrownBy(() -> cart.replaceItemWithFixedQuantity(createProductWithId(PRODUCT_ID_ONE), -1))
                    .as("Should throw an exception when quantity is less than zero")
                    .isInstanceOf(QuantityLessThanZeroException.class)
                    .hasMessage("Quantity must be greater than zero");
        }
    }

    public static Product createProductWithId(Long id) {
        return Product.createValid(id, DEFAULT_PRODUCT_NAME, DEFAULT_PRICE);
    }
}