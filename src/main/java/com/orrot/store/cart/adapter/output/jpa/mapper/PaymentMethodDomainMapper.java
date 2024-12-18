package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import com.orrot.store.cart.domain.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodDomainMapper {

    PaymentMethod mapToDomain(PaymentMethodJpaEntity entity);

    PaymentMethodJpaEntity mapToJpaEntity(PaymentMethod cart);
}
