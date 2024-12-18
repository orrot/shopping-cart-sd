package com.orrot.store.cart.adapter.input.mapper;

import com.orrot.store.cart.adapter.input.json.CartItemView;
import com.orrot.store.cart.adapter.input.json.CartView;
import com.orrot.store.cart.adapter.input.json.CartWrite;
import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapper;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.common.rest.json.IdentityCode;
import com.orrot.store.common.rest.json.IdentityCodeName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CartJsonViewMapperTest {

    private final CartJsonViewMapper mapper = Mappers.getMapper(CartJsonViewMapper.class);

    @Nested
    @DisplayName("When Mapping JSON Carts to Domain Carts")
    class WhenMappingJsonCartsToDomainCarts {

        @Test
        @DisplayName("Should map JSON View to Carts domain")
        void shouldMapToDomain() {
            var cartWrite = CartWrite.builder()
                    .id(1L)
                    .cartUserOwner("user1")
                    .paymentMethod(new IdentityCode("VISA"))
                    .build();

            var cart = mapper.mapToDomain(cartWrite);

            assertThat(cart)
                    .isNotNull()
                    .extracting(Cart::getId, Cart::getCartUserOwner)
                    .containsExactly(1L, "user1");

            assertThat(cart.getPaymentMethod())
                    .isNotNull()
                    .extracting(PaymentMethod::getCode)
                    .isEqualTo("VISA");
        }
    }

    @Nested
    @DisplayName("When Mapping Carts Domain to JSON Representation")
    class WhenMappingCartsDomainToJSONRepresentation {

        @Test
        @DisplayName("Should map Carts to JSON view")
        void shouldMapToDomain() {
           var cart = Cart.builder()
                    .id(1L)
                    .cartUserOwner("user1")
                    .paymentMethod(PaymentMethod.builder()
                            .code("VISA")
                            .name("Visa Payment")
                            .build())
                    .build();

           cart.addItems(1L, "product1", BigDecimal.valueOf(10_000), 5);

            var cartView = mapper.mapToView(cart);

            assertThat(cartView)
                    .isNotNull()
                    .extracting(CartView::id, CartView::cartUserOwner)
                    .containsExactly(1L, "user1");

            assertThat(cartView.paymentMethod())
                    .isNotNull()
                    .extracting(IdentityCodeName::code, IdentityCodeName::name)
                    .containsExactly("VISA", "Visa Payment");

            assertThat(cartView.items())
                    .isNotEmpty()
                    .extracting(CartItemView::productId, CartItemView::productName, CartItemView::currentPrice, CartItemView::quantity)
                    .containsExactly(tuple(1L, "product1", BigDecimal.valueOf(10_000), 5));
        }
    }
}