package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.model.Cart;

// It could be named "ForAddingItemToCart".
public interface CreateEmptyCartUseCase {
    Cart createEmptyCart(Cart cart);
}
