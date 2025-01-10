package com.orrot.store.product.domain.service.impl;

import com.orrot.store.product.adapter.output.ProductRepository;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(@NotNull @Valid Product productToCreate) {

        return productRepository.create(productToCreate);
    }

    @Override
    public Product update(@NotNull @Valid Product productToUpdate) {

        return productRepository.update(
                productToUpdate.getId(), productToUpdate);
    }

    @Override
    public Optional<Product> findById(Long productId) {

        return productRepository.findById(productId);
    }

}
