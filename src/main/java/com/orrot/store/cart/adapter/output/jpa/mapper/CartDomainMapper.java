package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.jpa.BaseDomainMapper;
import com.orrot.store.onlineuser.adapter.output.jpa.entity.OnlineClientJpaEntity;
import com.orrot.store.product.domain.model.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        uses = { CartItemDomainMapper.class, PaymentMethodDomainMapper.class })
public interface CartDomainMapper extends BaseDomainMapper<Cart, CartJpaEntity> {

    @Mapping(target = "onlineClientOwner.id", source = "onlineClientOwnerId")
    CartJpaEntity mapToJpaEntity(Cart domain);

    @InheritInverseConfiguration(name = "mapToJpaEntity")
    Cart mapToDomain(CartJpaEntity entity);

    @Mapping(target = "paymentMethod", expression = "java(paymentMethodDomainMapper.mapToJpaEntity(domain.getPaymentMethod()))")
    @Mapping(target = "onlineClientOwner", source = "onlineClientOwnerId", qualifiedByName = "mapToOnlineClientOwner")
    CartJpaEntity mapToExistingEntity(Cart domain, @MappingTarget CartJpaEntity entity);


    // Special method mappings
    @Named("mapToOnlineClientOwner")
    default OnlineClientJpaEntity mapToOnlineClientOwner(Long onlineClientOwnerId) {
        return Optional.ofNullable(onlineClientOwnerId)
                .map(id -> OnlineClientJpaEntity.builder().id(id).build())
                .orElse(null);
    }

    @AfterMapping
    default void mapEntityItems(@MappingTarget CartJpaEntity entity, Cart domain) {
        if (domain.getOnlineClientOwnerId() == null) {
            entity.setOnlineClientOwner(null);
        }
        // Set parent reference so JPA could handle all operations over children
        var items = new ArrayList<>(entity.getItems());
        items.forEach(item -> item.setCart(entity));
    }

    @AfterMapping
    default void mapItems(CartJpaEntity entity, @MappingTarget Cart domain) {
        CollectionUtils.emptyIfNull(entity.getItems())
                .forEach(entityItem -> {
                    var productEntity = Objects.requireNonNull(entityItem.getProduct(), "Product is required");
                    var product = Product.createValid(productEntity.getId(), productEntity.getName(), productEntity.getCurrentPrice());
                    domain.addItems(product, entityItem.getQuantity());
                });
    }
}
