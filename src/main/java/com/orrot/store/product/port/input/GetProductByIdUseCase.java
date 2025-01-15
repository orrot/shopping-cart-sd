package com.orrot.store.product.port.input;

import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.domain.service.ProductService;
import com.orrot.store.product.port.usecase.GetProductByIdInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductByIdUseCase implements GetProductByIdInputPort {

    private final ProductService productService;

    @Override
    public Product findById(Long productId) {
        return productService.findById(productId)
                .orElseThrow(() -> new UnExistingResourceException(
                        "Product ID '%d' does not exist".formatted(productId)));
    }
}
