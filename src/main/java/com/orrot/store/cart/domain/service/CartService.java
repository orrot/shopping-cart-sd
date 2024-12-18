package com.orrot.store.cart.domain.service;

import com.orrot.store.cart.domain.model.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface CartService {

    Cart createEmptyCart(@NotNull @Valid Cart cartToCreate);

    void updateCart(@NotNull @Valid Cart cartToUpdate);

    Optional<Cart> findById(Long cartId);
}
