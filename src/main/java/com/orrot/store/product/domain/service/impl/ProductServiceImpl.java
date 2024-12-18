package com.orrot.store.product.domain.service.impl;

import com.orrot.store.product.adapter.output.ProductRepository;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }
}
