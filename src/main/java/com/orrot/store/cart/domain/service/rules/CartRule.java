package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.specification.BusinessSpecification;
import com.orrot.store.common.specification.BusinessRuleResult;

import java.util.Collection;

public sealed abstract class CartRule implements BusinessSpecification<Cart>
        permits RegisteredClientRule, SupportedPaymentMethodRule {

    public static BusinessSpecification<Cart> combineRules(Cart cart, Collection<CartRule> cartRules) {
        return BusinessSpecification.and(cart, cartRules);
    }
}
