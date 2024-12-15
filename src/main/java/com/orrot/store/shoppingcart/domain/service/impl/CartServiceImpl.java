package com.orrot.store.shoppingcart.domain.service.impl;

import com.orrot.store.common.exception.UnExistingEntityException;
import com.orrot.store.common.exception.UnExistingRelationshipException;
import com.orrot.store.shoppingcart.adapter.output.CartRepository;
import com.orrot.store.shoppingcart.adapter.output.ProductRepository;
import com.orrot.store.shoppingcart.domain.model.Cart;
import com.orrot.store.shoppingcart.domain.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public void addOrUpdateCartItems(Long cartId, Long productId, int quantity) {

        var cart = findCartById(cartId);

        // In a more scalable design, Product might be outside the Cart scope
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new UnExistingRelationshipException(
                        "Product ID '%d' does not exist".formatted(productId)));

        cart.addOrUpdateItem(product.getId(), product.getPrice(), quantity);
        cartRepository.update(cart);
    }

    @Override
    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new UnExistingEntityException(
                        "Cart ID '%d' does not exist".formatted(cartId)));
    }
}
