package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.output.CartRepository;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.port.usecase.GetCartSummaryUseCase;
import com.orrot.store.common.exception.UnExistingEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCartSummaryInputPort implements GetCartSummaryUseCase {

    private final CartRepository cartRepository;

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new UnExistingEntityException(
                        "Cart ID '%d' does not exist".formatted(cartId)));
    }

}
