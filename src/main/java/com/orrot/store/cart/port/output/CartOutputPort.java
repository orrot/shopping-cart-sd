package com.orrot.store.cart.port.output;

import com.orrot.store.cart.domain.model.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface CartOutputPort {

    Cart create(@NotNull @Valid Cart cartToCreate);

    Cart update(@NotNull @Valid Cart cartToUpdate);

    Optional<Cart> findById(Long cartId);


    long countById(Long cartId);
}
