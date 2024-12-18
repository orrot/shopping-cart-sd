package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.CartItemWrite;
import com.orrot.store.cart.domain.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemJsonViewMapper {

    CartItem mapToDomain(CartItemWrite jsonView);
}
