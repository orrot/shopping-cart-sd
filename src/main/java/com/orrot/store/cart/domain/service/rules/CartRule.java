package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.specification.BusinessSpecification;

import java.util.Collection;

public sealed abstract class CartRule implements BusinessSpecification<Cart>
        permits RegisteredClientRule, SupportedPaymentMethodRule {

    public static BusinessSpecification<Cart> combineRules(Collection<CartRule> cartRules) {
        return BusinessSpecification.and(cartRules);
    }
}
