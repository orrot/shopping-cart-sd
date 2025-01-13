package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.domain.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemJsonViewMapperTest {

    private CartItemJsonViewMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CartItemJsonViewMapper.class);
    }

    @Nested
    @DisplayName("When Mapping JSON Cart Items to Domain Cart Items")
    class WhenMappingJsonCartItemsToDomainCartItems {

        @Test
        @DisplayName("Should map JSON View to Cart Item domain")
        void shouldMapToDomain() {
            // Given
            var cartItemPatch = CartItemPatch.builder()
                    .productId(1L)
                    .quantity(2)
                    .build();

            // When
            var cartItem = mapper.mapToDomain(cartItemPatch);

            // Then
            assertThat(cartItem)
                    .isNotNull()
                    .extracting(CartItem::getProductId, CartItem::getQuantity)
                    .containsExactly(1L, 2);
        }
    }
}