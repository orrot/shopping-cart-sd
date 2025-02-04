package com.orrot.store.cart.domain.service.impl;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.domain.service.rules.CartRule;
import com.orrot.store.cart.port.output.CartOutputPort;
import com.orrot.store.common.StoreConstants;
import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.exception.GeneralShoppingCartException;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class CartServiceImpl implements CartService {

    private final CartOutputPort cartOutputPort;
    // Rules: RegisteredUserRule, SupportedPaymentMethodRule
    private final List<CartRule> cartRules;

    @Override
    public Cart createEmptyCart(@NotNull @Valid Cart cartToCreate) {

        throwExceptionIfBrokenRule(cartToCreate);
        return Either.<GeneralShoppingCartException, Cart>
                        right(cartToCreate)
                .filterOrElse(cart -> cart.getId() == null,
                        cart -> new BusinessRuleException(
                                "The cart ID must be null to be created"))
                .map(cart -> cartOutputPort.create(cartToCreate))
                .getOrElseThrow(Function.identity());
    }

    @Override
    public void updateCart(@NotNull @Valid Cart cartToUpdate) {
        throwExceptionIfBrokenRule(cartToUpdate);
        cartOutputPort.update(cartToUpdate);
    }

    @Override
    public Optional<Cart> findById(Long cartId) {
        return cartOutputPort.findById(cartId);
    }

    private void throwExceptionIfBrokenRule(Cart cartToProcess) {
        var combinedResult = CartRule.combineRules(cartToProcess, cartRules)
                .isSatisfiedBy(cartToProcess);
        if (combinedResult.isRuleNotSatisfied()) {
            throw new BusinessRuleException(
                    StringUtils.join(combinedResult.notifications(), StoreConstants.DEFAULT_MESSAGE_DELIMITER));
        }
    }
}
