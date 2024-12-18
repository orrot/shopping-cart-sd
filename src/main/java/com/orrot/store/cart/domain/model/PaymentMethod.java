package com.orrot.store.cart.domain.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class PaymentMethod {

    @NotEmpty(message = "Code in payment must not be empty")
    @NonNull
    private String code;

    private String name;

    @Builder.Default
    private BigDecimal fixedFee = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal percentageFee = BigDecimal.ZERO;

    // Maybe this is not fixed for all the payment methods and could return different formulas
    public UnaryOperator<BigDecimal> getFormula() {
        return total -> {
            var fixedValueToUse = Objects.requireNonNullElse(fixedFee, BigDecimal.ZERO);
            var percentageValueToUse = Objects.requireNonNullElse(percentageFee, BigDecimal.ZERO);
            return total.add(total.multiply(percentageValueToUse)).add(fixedValueToUse);
        };
    }
}
