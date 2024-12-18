package com.orrot.store.product.domain.service;

import com.orrot.store.product.domain.model.Product;

import java.util.Optional;

public interface ProductService {

    Optional<Product> findById(Long productId);
}
