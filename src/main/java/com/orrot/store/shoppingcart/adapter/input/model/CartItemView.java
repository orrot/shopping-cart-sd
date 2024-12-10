package com.orrot.store.shoppingcart.adapter.input.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CartItemView(

        @Schema(example = "1") Long productId,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) String productName,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) BigDecimal currentPrice,

        @Schema(example = "1", defaultValue = "1") int quantity) {

}
