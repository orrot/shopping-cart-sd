package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.output.CartRepository;
import com.orrot.store.cart.port.usecase.CountCartItemsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountCartItemsInputPort implements CountCartItemsUseCase {

    private final CartRepository cartRepository;

    @Override
    public long countCartItems(Long cartId) {
        return cartRepository.findSumOfItems(cartId);
    }

}
