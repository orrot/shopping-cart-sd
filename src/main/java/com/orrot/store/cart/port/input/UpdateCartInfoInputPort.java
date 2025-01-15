package com.orrot.store.cart.port.input;

public interface UpdateCartInfoInputPort {

    void updateCartInfo(Long cartId, String paymentMethodCode, Long onlineClientIdToAssociate);
}
