package com.orrot.store.cart.adapter.input.json;

import lombok.Builder;

import java.math.BigDecimal;

public record PaymentMethodView(String code,
                                String name,
                                BigDecimal fixedFee,
                                BigDecimal percentageFee) {
}
