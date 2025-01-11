package com.orrot.store.product.adapter.input.json;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record ProductWrite(String name, BigDecimal currentPrice, String description) {
}
