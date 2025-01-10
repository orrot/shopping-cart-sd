package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.common.jpa.BaseDomainMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodDomainMapper extends BaseDomainMapper<PaymentMethod, PaymentMethodJpaEntity> {

    PaymentMethod mapToDomain(PaymentMethodJpaEntity entity);

    PaymentMethodJpaEntity mapToJpaEntity(PaymentMethod cart);
}
