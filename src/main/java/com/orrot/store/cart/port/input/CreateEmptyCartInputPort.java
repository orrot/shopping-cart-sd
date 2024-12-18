package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.CreateEmptyCartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmptyCartInputPort implements CreateEmptyCartUseCase {

    private final CartService cartService;

    @Override
    public Cart createEmptyCart(Cart cart) {
        return cartService.createEmptyCart(cart);
    }

}
