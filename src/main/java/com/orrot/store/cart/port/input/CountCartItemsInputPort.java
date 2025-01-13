package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.output.CartRepository;
import com.orrot.store.cart.port.usecase.CountCartItemsUseCase;
import com.orrot.store.common.exception.UnExistingResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountCartItemsInputPort implements CountCartItemsUseCase {

    private final CartRepository cartRepository;

    @Override
    public long countCartItems(Long cartId) {
        return Optional.ofNullable(cartId)
                .filter(cartRepository::existsById)
                .map(cartRepository::findSumOfItems)
                .orElseThrow(() -> new UnExistingResourceException("Cart with ID '%d' does not exist", cartId));
    }

}
