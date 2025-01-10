package com.orrot.store.cart.adapter.input.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.orrot.store.common.rest.json.IdentityCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Optional;

@Builder(toBuilder = true)
@JsonPropertyOrder({"id", "paymentMethod", "cartUserOwner"})
public record CartWrite(
        IdentityCode paymentMethod,
        @Schema(nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED) Long onlineClientIdOwner) {


    public String paymentMethodCode() {
        return Optional.ofNullable(paymentMethod)
                .map(IdentityCode::code)
                .orElse(null);
    }

}
