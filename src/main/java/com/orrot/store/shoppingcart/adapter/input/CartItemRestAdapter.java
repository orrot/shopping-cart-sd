package com.orrot.store.shoppingcart.adapter.input;

import com.orrot.store.shoppingcart.adapter.input.model.CartItemWrite;
import com.orrot.store.shoppingcart.port.usecase.AddOrUpdateCartItemsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/carts/{cartId}/items")
@Tag(name = "Carts")
// TODO Document rest api
public class CartItemRestAdapter {

    private final AddOrUpdateCartItemsUseCase addOrUpdateCartItemsUseCase;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Add the quantity specified to the item cart. If the quantity is 0, the item is removed from the cart.")
    public void addOrUpdateItemFromCart(@PathVariable("cartId") Long cartId,
                                        @RequestBody CartItemWrite cartItem) {
        addOrUpdateCartItemsUseCase
                .addOrUpdateCartItems(cartId, cartItem.productId(), cartItem.quantity());
    }
}
