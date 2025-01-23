package com.orrot.store.product.port.output;

import com.orrot.store.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductOutputPort {

    Optional<Product> findById(Long productId);

    Product create(Product productToCreate);

    Product update(Product productToUpdate);

    Page<Product> findAll(Pageable pageable);
}
