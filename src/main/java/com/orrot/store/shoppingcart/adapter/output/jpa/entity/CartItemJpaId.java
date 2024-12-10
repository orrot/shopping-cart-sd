package com.orrot.store.shoppingcart.adapter.output.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemJpaId {

    private Long cart;
    private Long productId;

}
