package com.orrot.store.product.port.input;

import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
import com.orrot.store.product.port.usecase.CreateProductInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase implements CreateProductInputPort {

    private final ProductService productService;

    @Override
    public Product createProduct(Product product) {
        return productService.create(product);
    }
}
