package com.orrot.store.shoppingcart.adapter.input;

import com.orrot.store.shoppingcart.adapter.input.model.CartItemView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/carts/{cartId}/items")
@Tag(name = "Carts")
public class CartItemRestAdapter {

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItemToCart(@RequestBody CartItemView cartItem) {

    }

    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromCart(Long productId) {

    }
}
