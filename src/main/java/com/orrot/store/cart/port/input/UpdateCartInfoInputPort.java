package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.UpdateCartInfoUseCase;
import com.orrot.store.common.exception.UnExistingEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCartInfoInputPort implements UpdateCartInfoUseCase {

    private final CartService cartService;

    @Override
    public void updateCartInfo(Long cartId, String paymentMethodCode, String cartUserOwner) {

        var cart = cartService.findById(cartId)
                .orElseThrow(() -> new UnExistingEntityException(
                        "Cart ID '%d' must exist to be updated", cartId));

        // Only payment and owner can be updated
        if (paymentMethodCode != null) {
            cart.updatePaymentMethod(paymentMethodCode);
        }
        if (cartUserOwner != null) {
            cart.associateCartToOwner(cartUserOwner);
        }
        cartService.updateCart(cart);
    }
}
