package com.orrot.store.shoppingcart.domain.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record PaymentMethod(String code,
                            String name,
                            FeeCalculationMethod feeCalcMethod,
                            BigDecimal feeValue) {

}
