package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.CartItemJpaEntity;
import com.orrot.store.cart.domain.model.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CartItemDomainMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "currentPrice", source = "product.currentPrice")
    CartItem mapToDomain(CartItemJpaEntity entity);

    CartItemJpaEntity mapToJpaEntity(CartItem cart);
}
