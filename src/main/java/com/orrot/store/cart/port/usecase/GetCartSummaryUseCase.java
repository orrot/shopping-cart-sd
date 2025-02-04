package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.input.GetCartSummaryInputPort;
import com.orrot.store.common.exception.UnExistingResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCartSummaryUseCase implements GetCartSummaryInputPort {

    private final CartService cartService;

    @Override
    public Cart findCartById(Long cartId) {
        return cartService.findById(cartId)
                .orElseThrow(() -> new UnExistingResourceException(
                        "Cart ID '%d' does not exist".formatted(cartId)));
    }

}
