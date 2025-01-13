package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.domain.service.CartService;
import com.orrot.store.cart.port.usecase.AddOrUpdateCartItemsUseCase;
import com.orrot.store.common.exception.UnExistingRelationshipException;
import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.product.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AddOrUpdateCartItemsInputPort implements AddOrUpdateCartItemsUseCase {

    private final CartService cartService;
    private final ProductService productService;

    @Override
    public void addOrUpdateCartItem(CartItemPatch.CartOperation operation, Long cartId, Long productId, int quantity) {

        var cart = cartService.findById(cartId)
                .orElseThrow(() -> new UnExistingResourceException(
                        "Cart ID '%d' of the item to be added must exist", cartId));
        var product = productService.findById(productId)
                .orElseThrow(() -> new UnExistingRelationshipException(
                        "Product ID '%d' does not exist".formatted(productId)));
        switch (operation) {
            case ADD ->
                    cart.addItems(product, quantity);
            case REMOVE ->
                    cart.removeItems(product, quantity);
            case SET_FIXED_QUANTITY ->
                    cart.replaceItemWithFixedQuantity(product, quantity);
        }
        cartService.updateCart(cart);
    }
}
