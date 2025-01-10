package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.specification.AbstractSpecification;
import com.orrot.store.common.specification.BusinessRuleResult;

import java.util.Collection;

public sealed abstract class CartRule extends AbstractSpecification<Cart>
        permits RegisteredUserRule, SupportedPaymentMethodRule {

    public static BusinessRuleResult checkAllSatisfied(Cart cart, Collection<CartRule> cartRules) {
        return AbstractSpecification.checkAllRules(cart, cartRules);
    }
}
