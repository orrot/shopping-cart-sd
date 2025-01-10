package com.orrot.store.product.domain.service;

import com.orrot.store.product.domain.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface ProductService {

    Product create(@NotNull @Valid Product productToCreate);

    Product update(@NotNull @Valid Product productToUpdate);

    Optional<Product> findById(Long productId);
}
