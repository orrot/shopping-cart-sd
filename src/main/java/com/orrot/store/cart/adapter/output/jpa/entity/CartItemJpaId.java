package com.orrot.store.cart.adapter.output.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemJpaId {

    private Long cart;
    private Long productId;

}
