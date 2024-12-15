package com.orrot.store.shoppingcart.port.output;

import com.orrot.store.shoppingcart.domain.model.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface CartOutputPort {

    Cart create(@NotNull @Valid Cart cartToCreate);

    Cart update(@NotNull @Valid Cart cartToUpdate);

    Optional<Cart> findById(Long cartId);

    boolean existsById(Long cartId);
}
