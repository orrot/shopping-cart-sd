package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.PaymentMethodView;
import com.orrot.store.cart.domain.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodJsonViewMapper {


    PaymentMethodView mapToView(PaymentMethod domain);

}
