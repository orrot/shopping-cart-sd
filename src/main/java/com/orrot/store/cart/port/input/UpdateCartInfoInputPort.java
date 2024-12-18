package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.UpdateCartInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCartInfoInputPort implements UpdateCartInfoUseCase {

    private final CartService cartService;

    @Override
    public void updateCartInfo(Long cartId, String paymentMethodCode, String cartUserOwner) {

        cartService.updateEditableCartInfo(cartId, paymentMethodCode, cartUserOwner);
    }
}
