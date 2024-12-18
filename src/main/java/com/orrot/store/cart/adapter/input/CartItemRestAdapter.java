package com.orrot.store.cart.adapter.input;

import com.orrot.store.cart.adapter.input.json.CartItemWrite;
import com.orrot.store.cart.port.usecase.AddOrUpdateCartItemsUseCase;
import com.orrot.store.cart.port.usecase.CountCartItemsUseCase;
import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.common.rest.json.TotalCount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ResourcesURI.CART_ITEMS_URI)
@Tag(name = ResourcesURI.CART_ITEMS_TAG)
public class CartItemRestAdapter {

    private final AddOrUpdateCartItemsUseCase addOrUpdateCartItemsUseCase;
    private final CountCartItemsUseCase countCartItemsUseCase;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Add a particular product to the Cart in an specified quantity. If the quantity is 0, the item is removed from the cart.")
    public void addOrUpdateCartItem(@PathVariable("cartId") Long cartId,
                                    @RequestBody CartItemWrite cartItem) {
        addOrUpdateCartItemsUseCase
                .addOrUpdateCartItem(cartId, cartItem.productId(), cartItem.quantity());
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns only the count of the cart items for the specified Cart ID.")
    public TotalCount countCartItems(@PathVariable("cartId") Long cartId) {
        // Consider combine Query Params and Specifications for more dynamic queries
        return new TotalCount(
                countCartItemsUseCase.countCartItems(cartId));
    }
}
