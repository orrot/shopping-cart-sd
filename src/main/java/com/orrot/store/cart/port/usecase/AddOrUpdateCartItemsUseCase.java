package com.orrot.store.cart.port.usecase;

// It could be named "ForAddingItemToCart".
public interface AddOrUpdateCartItemsUseCase {
    void addOrUpdateCartItem(Long cartId, Long productId, int quantity);
}
