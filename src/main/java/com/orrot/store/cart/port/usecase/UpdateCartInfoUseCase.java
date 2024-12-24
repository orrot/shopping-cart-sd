package com.orrot.store.cart.port.usecase;

public interface UpdateCartInfoUseCase {

    void updateCartInfo(Long cartId, String paymentMethodCode, Long onlineClientIdToAssociate);
}
