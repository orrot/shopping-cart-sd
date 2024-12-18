package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.CartView;
import com.orrot.store.cart.adapter.input.json.CartWrite;
import com.orrot.store.cart.domain.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartJsonViewMapper {


    Cart mapToDomain(CartWrite jsonView);

    CartView mapToView(Cart domain);
}
