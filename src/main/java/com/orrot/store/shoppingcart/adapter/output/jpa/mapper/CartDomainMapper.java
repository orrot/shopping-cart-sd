package com.orrot.store.shoppingcart.adapter.output.jpa.mapper;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.shoppingcart.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        uses = {PaymentMethodDomainMapper.class})
@RequiredArgsConstructor
public abstract class CartDomainMapper {

    @Autowired
    private CartItemDomainMapper cartItemDomainMapper;

    public abstract CartJpaEntity mapToJpaEntity(Cart domain);

    public abstract Cart mapToDomain(CartJpaEntity entity);

    @Mapping(target = "cartItems", ignore = true)
    public abstract CartJpaEntity mapToExistingEntity(Cart domain, @MappingTarget CartJpaEntity entity);;


    @AfterMapping
    void mapEntityItems(@MappingTarget CartJpaEntity entity, Cart domain) {
        var entityCartItems = domain.getCartItems()
                .stream()
                .map(cartItemDomainMapper::mapToJpaEntity)
                .collect(Collectors.toList());
        entity.setCartItems(entityCartItems);
    }

    @AfterMapping
    void mapItems(CartJpaEntity entity, @MappingTarget Cart domain) {
        entity.getCartItems()
                .stream()
                .map(cartItemDomainMapper::mapToDomain)
                .forEach(item -> domain.addOrUpdateItem(
                        item.getProductId(), item.getProductName(), item.getCurrentPrice(), item.getQuantity()));
    }
}
