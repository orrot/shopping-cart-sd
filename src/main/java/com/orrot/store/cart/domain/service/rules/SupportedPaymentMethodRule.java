package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import com.orrot.store.common.specification.BusinessRuleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class SupportedPaymentMethodRule extends CartRule {

    public static final String ERROR_UNSUPPORTED_PAYMENT_METHOD = "The Payment Method '%s' is not supported. Please use a valid one.";

    private final PaymentMethodOutputPort paymentMethodOutputPort;

    @Override
    public BusinessRuleResult isSatisfiedBy(Cart cart) {
        return isValidCartPaymentMethod(cart) ?
                BusinessRuleResult.SUCCESS :
                BusinessRuleResult.withError(ERROR_UNSUPPORTED_PAYMENT_METHOD
                        .formatted(cart.getPaymentMethodCode()));
    }

    private boolean isValidCartPaymentMethod(Cart cart) {
        return Optional.ofNullable(cart)
                .map(Cart::getPaymentMethodCode)
                .map(paymentMethodOutputPort::existsById)
                .orElse(false);
    }
}
