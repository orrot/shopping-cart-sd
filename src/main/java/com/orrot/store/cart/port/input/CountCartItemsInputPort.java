package com.orrot.store.cart.port.input;

import com.orrot.store.cart.port.output.CartOutputPort;
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

    private final CartOutputPort cartOutputPort;

    @Override
    public long countCartItems(Long cartId) {
        return Optional.ofNullable(cartId)
                .filter(cartOutputPort::existsById)
                .map(cartOutputPort::findSumOfItems)
                .orElseThrow(() -> new UnExistingResourceException("Cart with ID '%d' does not exist", cartId));
    }

}
