package com.orrot.store.cart.domain.service.impl;

import com.orrot.store.cart.adapter.output.CartRepository;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.domain.service.rules.CartRule;
import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.exception.GeneralShoppingCartException;
import com.orrot.store.common.exception.UnExistingEntityException;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    // Rules: RegisteredUserRule, SupportedPaymentMethodRule
    private final List<CartRule> cartRules;

    @Override
    public Cart createEmptyCart(@NotNull @Valid Cart cartToCreate) {

        throwExceptionIfBrokenRule(cartToCreate, "The cart cannot be created");
        return Either.<GeneralShoppingCartException, Cart>
                        right(cartToCreate)
                .filterOrElse(cart -> cart.getId() == null,
                        cart -> new UnExistingEntityException(
                                "The cart ID must be null to be created"))
                .map(cart -> cartRepository.create(cartToCreate))
                .getOrElseThrow(Function.identity());
    }

    @Override
    public void updateCart(@NotNull @Valid Cart cartToUpdate) {
        throwExceptionIfBrokenRule(cartToUpdate, "The cart cannot be updated");
        cartRepository.update(cartToUpdate);
    }

    @Override
    public Optional<Cart> findById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    private void throwExceptionIfBrokenRule(Cart cartToCreate, String generalErrorMessage) {
        var brokenRules = CartRule.checkAllSatisfied(cartToCreate, cartRules);
        if (!brokenRules.isEmpty()) {
            throw new BusinessRuleException(generalErrorMessage, brokenRules);
        }
    }
}
