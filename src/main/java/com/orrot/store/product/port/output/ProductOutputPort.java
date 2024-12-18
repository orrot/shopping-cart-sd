package com.orrot.store.product.port.output;

import com.orrot.store.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductOutputPort {
    Optional<Product> findById(Long productId);

    boolean existsById(Long productId);

    Page<Product> findAll(Pageable pageable);
}
