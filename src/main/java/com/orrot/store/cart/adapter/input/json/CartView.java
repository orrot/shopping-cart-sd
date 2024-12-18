package com.orrot.store.cart.adapter.input.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.orrot.store.common.rest.json.IdentityCodeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "paymentMethod", "cartUserOwner", "items"})
public record CartView(
        @Schema(description = "Generated by the system", accessMode = Schema.AccessMode.READ_ONLY) Long id,
        IdentityCodeName paymentMethod,
        String cartUserOwner,
        List<CartItemView> items) {

        // TODO Get the total amount of the cart and fee

}
