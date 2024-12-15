package com.orrot.store.shoppingcart.port.output;

import com.orrot.store.shoppingcart.domain.model.Product;

import java.util.Optional;

public interface ProductOutputPort {
    Optional<Product> findById(Long productId);

    boolean existsById(Long productId);
}
