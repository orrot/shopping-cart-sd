package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.model.Cart;

public interface GetCartSummaryUseCase {
    Cart getCartById(Long cartId);
}
