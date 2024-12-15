package com.orrot.store.shoppingcart.adapter.output;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.CartItemJpaEntity;
import com.orrot.store.shoppingcart.adapter.output.jpa.entity.CartItemJpaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItemJpaEntity, CartItemJpaId> {

}
