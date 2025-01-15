package com.orrot.store.product.port.input;

import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.port.output.ProductOutputPort;
import com.orrot.store.product.port.usecase.ListProductsInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListProductsUseCase implements ListProductsInputPort {

    private final ProductOutputPort productOutputPort;

    @Override
    public Page<Product> listProducts(Pageable pageable) {
        return productOutputPort.findAll(pageable);
    }
}
