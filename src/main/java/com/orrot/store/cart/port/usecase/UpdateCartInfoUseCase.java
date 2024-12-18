package com.orrot.store.cart.port.usecase;

// It could be named "ForAddingItemToCart".
public interface UpdateCartInfoUseCase {

    void updateCartInfo(Long cartId, String paymentMethodCode, String cartUserOwner);
}
