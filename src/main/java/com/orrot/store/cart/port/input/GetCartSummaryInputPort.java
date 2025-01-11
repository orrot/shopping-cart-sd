package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.GetCartSummaryUseCase;
import com.orrot.store.common.exception.UnExistingResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCartSummaryInputPort implements GetCartSummaryUseCase {

    private final CartService cartService;

    @Override
    public Cart getCartById(Long cartId) {
        return cartService.findById(cartId)
                .orElseThrow(() -> new UnExistingResourceException(
                        "Cart ID '%d' does not exist".formatted(cartId)));
    }

}
