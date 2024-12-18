package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.AddOrUpdateCartItemsUseCase;
import com.orrot.store.common.exception.UnExistingRelationshipException;
import com.orrot.store.product.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddOrUpdateCartItemsInputPort implements AddOrUpdateCartItemsUseCase {

    private final CartService cartService;
    private final ProductService productService;

    @Override
    public void addOrUpdateCartItem(Long cartId, Long productId, int quantity) {

        var product = productService.findById(productId)
                .orElseThrow(() -> new UnExistingRelationshipException(
                        "Product ID '%d' does not exist".formatted(productId)));

        cartService.addOrUpdateCartItem(cartId, product, quantity);
    }
}
