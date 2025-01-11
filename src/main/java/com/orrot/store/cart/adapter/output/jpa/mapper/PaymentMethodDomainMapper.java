package com.orrot.store.cart.adapter.output.jpa.mapper;

import com.orrot.store.cart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.common.jpa.BaseDomainMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodDomainMapper extends BaseDomainMapper<PaymentMethod, PaymentMethodJpaEntity> {

    @Mapping(target = "carts", ignore = true)
    PaymentMethodJpaEntity mapToJpaEntity(PaymentMethod domain);

    @InheritInverseConfiguration(name = "mapToJpaEntity")
    PaymentMethod mapToDomain(PaymentMethodJpaEntity entity);

    PaymentMethodJpaEntity mapToExistingEntity(PaymentMethod domain, @MappingTarget PaymentMethodJpaEntity entity);
}
