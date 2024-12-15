package com.orrot.store.shoppingcart.port.usecase;

// It could be named "ForAddingItemToCart".
public interface AddOrUpdateCartItemsUseCase {
    void addOrUpdateCartItems(Long cartId, Long productId, int quantity);
}
