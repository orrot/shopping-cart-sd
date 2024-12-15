package com.orrot.store.shoppingcart.adapter.input;

import com.orrot.store.shoppingcart.adapter.input.model.CartView;
import com.orrot.store.shoppingcart.adapter.input.model.IdentityId;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/carts")
@Tag(name = "Carts")
@RequiredArgsConstructor
// TODO Document rest API
public class CartRestAdapter {

    //private final CreateCartUseCase createCartUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdentityId createCart(@RequestBody CartView cart) {
        return new IdentityId(1L);
    }

    @PatchMapping("{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCart(@RequestBody CartView cart) {

    }

    @GetMapping("{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public CartView findCartById() {
        return new CartView(1L, null, 1L, null);
    }
}
