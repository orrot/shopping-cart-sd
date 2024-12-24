package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;

public interface AddOrUpdateCartItemsUseCase {

    void addOrUpdateCartItem(CartItemPatch.CartOperation operation, Long cartId, Long productId, int quantity);
}
