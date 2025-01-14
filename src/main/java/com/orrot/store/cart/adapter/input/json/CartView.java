package com.orrot.store.cart.adapter.input.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.orrot.store.common.rest.json.IdentityCodeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "paymentMethod", "onlineClientOwnerId", "items"})
public record CartView(
        @Schema(description = "Generated by the system", accessMode = Schema.AccessMode.READ_ONLY) Long id,
        IdentityCodeName paymentMethod,
        Long onlineClientOwnerId,
        BigDecimal total,
        BigDecimal totalWithFee,
        List<CartItemView> items) {

}
