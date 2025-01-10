package com.orrot.store.product.port.input;

import com.orrot.store.product.adapter.output.ProductRepository;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.port.usecase.ListProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListProductsInputPort implements ListProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> listProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
