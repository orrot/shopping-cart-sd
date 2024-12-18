package com.orrot.store.cart.domain.service.rules;

import com.orrot.store.cart.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public final class RegisteredUserRule extends CartRule {

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        // TODO Validate the user here
        log.info("=================================================================================User {} is valid", cart.getCartUserOwner());
        return true;
    }

    @Override
    public String getUnsatisfiedReason() {
        return "The User is not registered";
    }
}
