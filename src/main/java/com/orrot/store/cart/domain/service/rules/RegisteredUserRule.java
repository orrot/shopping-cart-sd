package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.common.specification.BusinessRuleResult;
import com.orrot.store.onlineuser.adapter.output.OnlineClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public final class RegisteredUserRule extends CartRule {

    public static final String ERROR_UNREGISTERED_USER = "The User must be registered so the cast could be assigned";

    private final OnlineClientRepository onlineClientRepository;

    @Override
    public BusinessRuleResult areSatisfiedBy(Cart cart) {
        return Optional.ofNullable(cart)
                .map(Cart::getOnlineClientIdOwner)
                .map(onlineClientRepository::existsById)
                .filter(Boolean::booleanValue)
                .map(BusinessRuleResult::new)
                .orElse(new BusinessRuleResult(false, ERROR_UNREGISTERED_USER));
    }
}
