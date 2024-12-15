package com.orrot.store.shoppingcart.domain.model;

import java.math.BigDecimal;

@FunctionalInterface
public interface FeeMethodFormula {

    BigDecimal apply(BigDecimal value, BigDecimal fixedValue, BigDecimal percentageValue);
}
