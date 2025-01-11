package com.orrot.store.product.port.usecase;

import com.orrot.store.product.domain.model.Product;

public interface GetProductByIdUseCase {

    Product findById(Long productId);
}
