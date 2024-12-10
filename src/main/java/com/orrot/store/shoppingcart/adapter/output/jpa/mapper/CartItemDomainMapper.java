package com.orrot.store.shoppingcart.adapter.output.jpa.mapper;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.CartItemJpaEntity;
import com.orrot.store.shoppingcart.domain.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemDomainMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "currentPrice", source = "product.currentPrice")
    CartItem mapToDomain(CartItemJpaEntity entity);

    CartItemJpaEntity mapToJpaEntity(CartItem cart);
}
