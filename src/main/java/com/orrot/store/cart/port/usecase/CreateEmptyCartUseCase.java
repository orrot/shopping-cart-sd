package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.input.CreateEmptyCartInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmptyCartUseCase implements CreateEmptyCartInputPort {

    private final CartService cartService;

    @Override
    public Cart createEmptyCart(Cart cart) {
        return cartService.createEmptyCart(cart);
    }

}
