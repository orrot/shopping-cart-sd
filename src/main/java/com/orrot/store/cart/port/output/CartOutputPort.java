package com.orrot.store.cart.port.output;

import com.orrot.store.cart.domain.model.Cart;

import java.util.Optional;

public interface CartOutputPort {

    Cart create(Cart cartToCreate);

    Cart update(Cart cartToUpdate);

    boolean existsById(Long id);

    Optional<Cart> findById(Long cartId);

    Long findSumOfItems(Long cartId);
}
