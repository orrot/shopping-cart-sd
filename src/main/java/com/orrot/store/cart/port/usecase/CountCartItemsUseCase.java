package com.orrot.store.cart.port.usecase;

// It could be named "ForAddingItemToCart".
public interface CountCartItemsUseCase {
    long countCartItems(Long cartId);
}
