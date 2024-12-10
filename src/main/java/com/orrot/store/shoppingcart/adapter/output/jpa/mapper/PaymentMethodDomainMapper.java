package com.orrot.store.shoppingcart.adapter.output.jpa.mapper;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.shoppingcart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import com.orrot.store.shoppingcart.domain.model.Cart;
import com.orrot.store.shoppingcart.domain.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodDomainMapper {

    PaymentMethod mapToDomain(PaymentMethodJpaEntity entity);

    PaymentMethodJpaEntity mapToJpaEntity(PaymentMethod cart);
}
