package com.orrot.store.cart.adapter.input.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@JsonPropertyOrder({"productId", "productName", "currentPrice", "quantity"})
public record CartItemView(

        @Schema(example = "1") Long productId,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) String productName,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) BigDecimal currentPrice,
        @Schema(example = "1", defaultValue = "1") int quantity) {

}
