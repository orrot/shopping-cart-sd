package com.orrot.store.product.domain.service.impl;

import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
import com.orrot.store.product.port.output.ProductOutputPort;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {

    private final ProductOutputPort productOutputPort;

    @Override
    public Product create(@NotNull @Valid Product productToCreate) {

        return productOutputPort.create(productToCreate);
    }

    @Override
    public Product update(@NotNull @Valid Product productToUpdate) {

        return productOutputPort.update(
                productToUpdate.getId(), productToUpdate);
    }

    @Override
    public Optional<Product> findById(Long productId) {

        return productOutputPort.findById(productId);
    }

}
