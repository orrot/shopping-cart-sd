package com.orrot.store.product.port.input;

import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
import com.orrot.store.product.port.usecase.UpdateProductInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCase implements UpdateProductInputPort {

    private final ProductService productService;

    @Override
    public void updateProduct(Product product) {
        productService.update(product);
    }
}
