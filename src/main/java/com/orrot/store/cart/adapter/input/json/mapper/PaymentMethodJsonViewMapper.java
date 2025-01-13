package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.PaymentMethodView;
import com.orrot.store.cart.domain.model.PaymentMethod;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PaymentMethodJsonViewMapper {


    PaymentMethodView mapToView(PaymentMethod domain);

}
