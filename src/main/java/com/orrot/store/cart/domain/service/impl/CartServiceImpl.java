package com.orrot.store.cart.domain.service.impl;

import com.orrot.store.cart.adapter.output.CartRepository;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.domain.service.rules.CartRule;
import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.exception.GeneralShoppingCartException;
import com.orrot.store.common.exception.UnExistingEntityException;
import com.orrot.store.product.domain.model.Product;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
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
    public void updateEditableCartInfo(Long cartId, String paymentMethodCode, String cartUserOwner) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new UnExistingEntityException(
                        "Cart ID '%d' must exist to be updated", cartId));

        // Only payment and owner can be updated
        if (paymentMethodCode != null) {
            cart.updatePaymentMethod(paymentMethodCode);
        }
        if (cartUserOwner != null) {
            cart.associateCartToOwner(cartUserOwner);
        }
        throwExceptionIfBrokenRule(cart, "The cart cannot be updated");

        cartRepository.update(cart);
    }

    @Override
    public void addOrUpdateCartItem(Long cartId, @Valid Product product, int quantity) {

        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new UnExistingEntityException(
                        "Cart ID '%d' of the item to be added must exist", cartId));
        cart.addOrUpdateItem(product.getId(), product.getPrice(), quantity);
        cartRepository.update(cart);
    }

    private void throwExceptionIfBrokenRule(Cart cartToCreate, String generalErrorMessage) {
        var brokenRules = CartRule.checkAllSatisfied(cartToCreate, cartRules);
        if (!brokenRules.isEmpty()) {
            throw new BusinessRuleException(generalErrorMessage, brokenRules);
        }
    }
}
