package com.orrot.store.shoppingcart.adapter.input.model;

import java.math.BigDecimal;

public record ProductView(Long id, String name, BigDecimal currentPrice) {
}
