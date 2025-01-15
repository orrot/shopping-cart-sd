package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;

public interface CreateEmptyCartInputPort {
    Cart createEmptyCart(Cart cart);
}
