package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.domain.model.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CartItemJsonViewMapper {

    @Mapping(target = "product.id", source = "productId")
    CartItem mapToDomain(CartItemPatch jsonView);
}
