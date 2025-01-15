package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;

public interface GetCartSummaryInputPort {
    Cart findCartById(Long cartId);
}
