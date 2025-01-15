package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.input.UpdateCartInfoInputPort;
import com.orrot.store.common.exception.UnExistingResourceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class UpdateCartInfoUseCase implements UpdateCartInfoInputPort {

    private final CartService cartService;

    @Override
    public void updateCartInfo(Long cartId, String paymentMethodCode, Long onlineClientIdToAssociate) {

        var cart = cartService.findById(cartId)
                .orElseThrow(() -> new UnExistingResourceException(
                        "Cart ID '%d' must exist to be updated", cartId));

        // Only payment and owner can be updated
        Optional.ofNullable(paymentMethodCode)
                .ifPresent(cart::updatePaymentMethod);
        Optional.ofNullable(onlineClientIdToAssociate)
                .ifPresent(cart::associateCartToOwner);

        cartService.updateCart(cart);
    }
}
