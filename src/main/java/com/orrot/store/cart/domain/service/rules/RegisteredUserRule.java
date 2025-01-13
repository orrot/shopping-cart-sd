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

    public static final String ERROR_UNREGISTERED_USER = "The User with id '%d' must be registered so the cart could be assigned";

    private final OnlineClientRepository onlineClientRepository;

    @Override
    public BusinessRuleResult isSatisfiedBy(Cart cart) {
        return !cart.isOnlineClientAssigned() || isOnlineClientRegistered(cart) ?
                BusinessRuleResult.SUCCESS :
                BusinessRuleResult.withError(ERROR_UNREGISTERED_USER
                        .formatted(cart.getOnlineClientOwnerId()));
    }

    private boolean isOnlineClientRegistered(Cart cart) {
        return Optional.ofNullable(cart)
                .map(Cart::getOnlineClientOwnerId)
                .map(onlineClientRepository::existsById)
                .orElse(false);
    }
}
