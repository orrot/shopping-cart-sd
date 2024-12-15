package com.orrot.store.shoppingcart.adapter.input.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartItemWrite(
        @Schema(example = "1") Long productId,
        @Schema(example = "1", defaultValue = "1", requiredMode = Schema.RequiredMode.REQUIRED) int quantity) {

}
