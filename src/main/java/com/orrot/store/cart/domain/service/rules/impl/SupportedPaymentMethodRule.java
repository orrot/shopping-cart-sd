package com.orrot.store.cart.domain.service.rules.impl;

import com.orrot.store.cart.adapter.output.PaymentMethodRepository;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.rules.CartRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupportedPaymentMethodRule extends CartRule {

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        return paymentMethodRepository.exists(cart.getPaymentMethod());
    }

    @Override
    public String getUnsatisfiedReason() {
        return "The Payment Method is not supported";
    }
}
