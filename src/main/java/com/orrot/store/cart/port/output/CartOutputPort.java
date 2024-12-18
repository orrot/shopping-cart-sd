package com.orrot.store.cart.port.output;

import com.orrot.store.cart.domain.model.Cart;

import java.util.Optional;

public interface CartOutputPort {

    Cart create(Cart cartToCreate);

    void update(Cart cartToUpdate);

    Optional<Cart> findById(Long cartId);


    long countById(Long cartId);
}
