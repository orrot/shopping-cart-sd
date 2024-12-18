package com.orrot.store.cart.adapter.input.json;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record CartItemPatch(
        @Schema(examples = { "ADD", "REMOVE", "SET_FIXED_QUANTITY" },
                description = "Only ADD, REMOVE and SET_FIXED_QUANTITY are allowed") @NotNull CartOperation operation,
        @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED) Long productId,
        @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED) int quantity) {


    public enum CartOperation {
        ADD, REMOVE, SET_FIXED_QUANTITY
    }

}
