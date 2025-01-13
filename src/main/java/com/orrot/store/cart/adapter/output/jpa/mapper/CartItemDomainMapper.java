package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.CartItemJpaEntity;
import com.orrot.store.cart.domain.model.CartItem;
import com.orrot.store.product.adapter.output.jpa.mapper.ProductDomainMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = ProductDomainMapper.class)
public interface CartItemDomainMapper {

    CartItem mapToDomain(CartItemJpaEntity entity);

    CartItemJpaEntity mapToJpaEntity(CartItem cart);
}
