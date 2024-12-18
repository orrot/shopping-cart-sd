package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.specification.AbstractSpecification;
import com.orrot.store.common.specification.BrokenRule;

import java.util.Collection;
import java.util.List;

public sealed abstract class CartRule extends AbstractSpecification<Cart>
        permits RegisteredUserRule, SupportedPaymentMethodRule {

    public static List<BrokenRule> checkAllSatisfied(Cart cart, Collection<CartRule> cartRules) {
        return AbstractSpecification.checkAllSatisfied(cart, cartRules);
    }
}
