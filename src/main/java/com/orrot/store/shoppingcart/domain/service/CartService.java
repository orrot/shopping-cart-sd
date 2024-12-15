package com.orrot.store.shoppingcart.domain.service;

import com.orrot.store.shoppingcart.domain.model.Cart;

public interface CartService {
    void addOrUpdateCartItems(Long cartId, Long productId, int quantity);

    Cart findCartById(Long cartId);
}
