package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.jpa.BaseDomainMapper;
import com.orrot.store.onlineuser.adapter.output.jpa.entity.OnlineClientJpaEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        uses = { PaymentMethodDomainMapper.class })
@RequiredArgsConstructor
public abstract class CartDomainMapper implements BaseDomainMapper<Cart, CartJpaEntity> {

    @Autowired
    private CartItemDomainMapper cartItemDomainMapper;

    @Mapping(target = "onlineClientOwner.id", source = "onlineClientOwnerId")
    public abstract CartJpaEntity mapToJpaEntity(Cart domain);

    @InheritInverseConfiguration(name = "mapToJpaEntity")
    public abstract Cart mapToDomain(CartJpaEntity entity);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "paymentMethod", expression = "java(paymentMethodDomainMapper.mapToJpaEntity(domain.getPaymentMethod()))")
    @Mapping(target = "onlineClientOwner", source = "onlineClientOwnerId", qualifiedByName = "mapToOnlineClientOwner")
    public abstract CartJpaEntity mapToExistingEntity(Cart domain, @MappingTarget CartJpaEntity entity);

    @Named("mapToOnlineClientOwner")
    OnlineClientJpaEntity mapToOnlineClientOwner(Long onlineClientOwnerId) {
        return Optional.ofNullable(onlineClientOwnerId)
                .map(id -> OnlineClientJpaEntity.builder().id(id).build())
                .orElse(null);
    }

    @AfterMapping
    void mapEntityItems(@MappingTarget CartJpaEntity entity, Cart domain) {
        var entityItems = domain.getItems()
                .stream()
                .map(cartItemDomainMapper::mapToJpaEntity)
                .collect(Collectors.toList());
        entity.setItems(entityItems);

        if (domain.getOnlineClientOwnerId() == null) {
            entity.setOnlineClientOwner(null);
        }
    }

    @AfterMapping
    void mapItems(CartJpaEntity entity, @MappingTarget Cart domain) {
        entity.getItems()
                .stream()
                .map(cartItemDomainMapper::mapToDomain)
                .forEach(item -> domain.addItems(
                        item.getProductId(), item.getProductName(), item.getCurrentPrice(), item.getQuantity()));
    }
}
