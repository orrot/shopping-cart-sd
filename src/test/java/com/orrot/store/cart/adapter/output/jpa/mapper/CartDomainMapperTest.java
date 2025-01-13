package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.CartItemJpaEntity;
import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.cart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.model.CartItem;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.onlineuser.adapter.output.jpa.entity.OnlineClientJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CartDomainMapperTest {

    private final CartDomainMapper mapper = new com.orrot.store.cart.adapter.output.jpa.mapper.CartDomainMapperImpl(
            Mappers.getMapper(CartItemDomainMapper.class),
            Mappers.getMapper(PaymentMethodDomainMapper.class));

    @DisplayName("When mapping domain to entity")
    @Nested
    class WhenMappingDomainToEntity {

        @Test
        @DisplayName("Should map domain to entity")
        void shouldMapDomainToEntity() {
            // Given
            var domain = MockerFactory.createDummy(Cart.class);
            domain.addItems(1L, "Product Name 1", BigDecimal.ONE, 1);
            domain.addItems(2L, "Product Name 2", BigDecimal.TWO, 2);

            // When
            var entity = mapper.mapToJpaEntity(domain);

            // Then
            assertThat(entity)
                    .isNotNull()
                    .extracting(CartJpaEntity::getId)
                    .isEqualTo(domain.getId());

            assertThat(entity.getPaymentMethod())
                    .isNotNull()
                    .extracting(PaymentMethodJpaEntity::getCode)
                    .as("The payment code is required to be mapped")
                    .isEqualTo(domain.getPaymentMethod().getCode());

            assertThat(entity.getOnlineClientOwner())
                    .isNotNull()
                    .extracting(OnlineClientJpaEntity::getId)
                    .as("The online client owner id is required to be mapped")
                    .isEqualTo(domain.getOnlineClientOwnerId());

            assertThat(entity.getItems())
                    .extracting(CartItemJpaEntity::getCart)
                    .as("All items should reference the parent in the entity")
                    .allMatch(Objects::nonNull);

            assertThat(entity.getItems())
                    .extracting(CartItemJpaEntity::getProductId, CartItemJpaEntity::getQuantity)
                    .as("Product ID and quantity should be mapped")
                    .containsExactly(
                            tuple(1L, 1), tuple(2L, 2));
        }
    }

    @DisplayName("When mapping entity to domain")
    @Nested
    class WhenMappingEntityToDomain {

        @Test
        @DisplayName("Should map domain to existing entity")
        void shouldMapDomainToExistingEntity() {

            // Given
            var itemEntity1 = MockerFactory.createDummy(CartItemJpaEntity.class);
            var itemEntity2 = MockerFactory.createDummy(CartItemJpaEntity.class);
            var entity = MockerFactory.createDummy(CartJpaEntity.class)
                    .withItems(List.of(itemEntity1, itemEntity2));

            // When
            var domain = mapper.mapToDomain(entity);

            // Then
            var entityPaymentMethod = entity.getPaymentMethod();

            assertThat(domain)
                    .isNotNull()
                    .extracting(Cart::getId, Cart::getPaymentMethodCode, Cart::getOnlineClientOwnerId)
                    .contains(entity.getId(), entityPaymentMethod.getCode(), entity.getOnlineClientOwner().getId());

            assertThat(domain.getItems())
                    .extracting(CartItem::getProductId, CartItem::getProductName, CartItem::getCurrentPrice, CartItem::getQuantity)
                    .containsExactlyInAnyOrder(
                            tuple(itemEntity1.getProductId(), itemEntity1.getProductName(), itemEntity1.getCurrentPrice(), itemEntity1.getQuantity()),
                            tuple(itemEntity2.getProductId(), itemEntity2.getProductName(), itemEntity2.getCurrentPrice(), itemEntity2.getQuantity()));
        }
    }

    @DisplayName("When mapping domain to existing entity")
    @Nested
    class WhenMappingDomainToExistingEntity {
        @Test
        @DisplayName("Should map domain to existing entity")
        void shouldMapDomainToExistingEntity() {
            // Given
            var domain = MockerFactory.createDummy(Cart.class);
            domain.addItems(1L, "Product Name 1", BigDecimal.ONE, 1);
            domain.addItems(2L, "Product Name 2", BigDecimal.TWO, 2);

            var existingEntity = mapper.mapToJpaEntity(domain);

            // When
            var entity = mapper.mapToExistingEntity(domain, existingEntity);

            assertThat(entity)
                    .isSameAs(existingEntity);

            // Then
            assertThat(entity)
                    .isNotNull()
                    .extracting(CartJpaEntity::getId)
                    .isEqualTo(domain.getId());

            assertThat(entity.getPaymentMethod())
                    .isNotNull()
                    .extracting(PaymentMethodJpaEntity::getCode)
                    .as("The payment code is required to be mapped")
                    .isEqualTo(domain.getPaymentMethod().getCode());

            assertThat(entity.getOnlineClientOwner())
                    .isNotNull()
                    .extracting(OnlineClientJpaEntity::getId)
                    .as("The online client owner id is required to be mapped")
                    .isEqualTo(domain.getOnlineClientOwnerId());

            assertThat(entity.getItems())
                    .extracting(CartItemJpaEntity::getCart)
                    .as("All items should reference the parent in the entity")
                    .allMatch(Objects::nonNull);

            assertThat(entity.getItems())
                    .extracting(CartItemJpaEntity::getProductId, CartItemJpaEntity::getQuantity)
                    .as("Product ID and quantity should be mapped")
                    .containsExactly(
                            tuple(1L, 1), tuple(2L, 2));
        }
    }
}