package com.orrot.store.shoppingcart.port.input;

import com.orrot.store.shoppingcart.domain.service.CartService;
import com.orrot.store.shoppingcart.port.usecase.AddOrUpdateCartItemsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddOrUpdateCartItemsInputPort implements AddOrUpdateCartItemsUseCase {

    private final CartService cartService;

    @Override
    public void addOrUpdateCartItems(Long cartId, Long productId, int quantity) {
        cartService.addOrUpdateCartItems(cartId, productId, quantity);
    }
}
