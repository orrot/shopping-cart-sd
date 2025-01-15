package com.orrot.store.cart.adapter.input;

import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.port.input.AddOrUpdateCartItemsInputPort;
import com.orrot.store.cart.port.input.CountCartItemsInputPort;
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

    private final AddOrUpdateCartItemsInputPort addOrUpdateCartItemsInputPort;
    private final CountCartItemsInputPort countCartItemsInputPort;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Allows to ADD, REMOVE or SET_FIXED_QUANTITY for a Cart Item. " +
            "ADD: Adds the quantity to the existing one. " +
            "REMOVE: Removes the quantity from the existing one. " +
            "SET_FIXED_QUANTITY: Sets the quantity to the specified product.")
    public void addOrUpdateCartItem(@PathVariable("cartId") Long cartId,
                                    @RequestBody CartItemPatch cartItem) {
        addOrUpdateCartItemsInputPort
                .addOrUpdateCartItem(cartItem.operation(), cartId, cartItem.productId(), cartItem.quantity());
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns only the count of the cart items for the specified Cart ID.")
    public TotalCount countCartItems(@PathVariable("cartId") Long cartId) {
        // Consider combine Query Params and Specifications for more dynamic queries
        return new TotalCount(
                countCartItemsInputPort.countCartItems(cartId));
    }
}
