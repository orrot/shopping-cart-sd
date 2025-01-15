package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;

public interface AddOrUpdateCartItemsInputPort {

    void addOrUpdateCartItem(CartItemPatch.CartOperation operation, Long cartId, Long productId, int quantity);
}
