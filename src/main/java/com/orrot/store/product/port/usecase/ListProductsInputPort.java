package com.orrot.store.product.port.usecase;

import com.orrot.store.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListProductsInputPort {

    Page<Product> listProducts(Pageable pageable);
}
