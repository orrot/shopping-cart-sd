package com.orrot.store.cart.adapter.input.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
public record CartItemWrite(
        @Schema(example = "1") Long productId,
        @Schema(example = "1", defaultValue = "1", requiredMode = Schema.RequiredMode.REQUIRED) int quantity) {

}
