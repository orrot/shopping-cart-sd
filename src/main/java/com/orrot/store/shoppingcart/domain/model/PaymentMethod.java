package com.orrot.store.shoppingcart.domain.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Builder(toBuilder = true)
public record PaymentMethod(
        @NotEmpty(message = "Code in payment must not be empty") String code,
        @NotEmpty(message = "Name in payment most not be empty") String name,
        BigDecimal fixedFee,
        BigDecimal percentageFee) {

    public UnaryOperator<BigDecimal> getFormula() {
        return total -> {
            var fixedValueToUse = Objects.requireNonNullElse(fixedFee, BigDecimal.ZERO);
            var percentageValueToUse = Objects.requireNonNullElse(percentageFee, BigDecimal.ZERO);
            return total.add(total.multiply(percentageValueToUse)).add(fixedValueToUse);
        };
    }
}
