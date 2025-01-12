package com.orrot.store.cart.adapter.input;

import com.orrot.store.cart.adapter.input.json.CartWrite;
import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapper;
import com.orrot.store.cart.port.usecase.CreateEmptyCartUseCase;
import com.orrot.store.cart.port.usecase.UpdateCartInfoUseCase;
import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.common.rest.json.IdentityId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourcesURI.CARTS_URI)
@Tag(name = ResourcesURI.CARTS_TAG)
@RequiredArgsConstructor
// TODO Document rest API
public class CartWriteRestAdapter {

    private final CartJsonViewMapper mapper;
    private final CreateEmptyCartUseCase createEmptyCartUseCase;
    private final UpdateCartInfoUseCase updateCartInfoUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new empty cart")
    public IdentityId createCart(@RequestBody CartWrite cartJson) {
        var cart = mapper.mapToDomain(cartJson);
        var createdCart = createEmptyCartUseCase.createEmptyCart(cart);
        return new IdentityId(createdCart.getId());
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Allows to update the cart payment method and user associated to the cart. No Other field can be modified")
    public void updateCart(
            @PathVariable("id") Long cartId,
            @RequestBody CartWrite cartJson) {
        updateCartInfoUseCase.updateCartInfo(
                cartId, cartJson.paymentMethodCode(), cartJson.onlineClientOwnerId());
    }
}
