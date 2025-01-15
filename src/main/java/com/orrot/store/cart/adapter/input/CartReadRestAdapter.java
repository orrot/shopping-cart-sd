package com.orrot.store.cart.adapter.input;

import com.orrot.store.cart.adapter.input.json.CartView;
import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapper;
import com.orrot.store.cart.port.input.GetCartSummaryInputPort;
import com.orrot.store.common.rest.ResourcesURI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ResourcesURI.CARTS_URI)
@Tag(name = ResourcesURI.CARTS_TAG)
@RequiredArgsConstructor
public class CartReadRestAdapter {

    private final CartJsonViewMapper mapper;
    private final GetCartSummaryInputPort getCartSummaryInputPort;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns the cart by id, including all the items")
    public CartView findCartById(@PathVariable("id") Long id) {
        // Query
        return Optional.ofNullable(id)
                .map(getCartSummaryInputPort::findCartById)
                .map(mapper::mapToView)
                .orElseThrow();
    }
}
