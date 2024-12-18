package com.orrot.store.cart.domain.service;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.product.domain.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CartService {
    Cart createEmptyCart(@NotNull @Valid Cart cartToCreate);

    void updateEditableCartInfo(Long cartId, String paymentMethodCode, String cartUserOwner);

    void addOrUpdateCartItem(Long cartId, @Valid Product product, int quantity);
}
