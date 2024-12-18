package com.orrot.store.cart.adapter.output.jpa;

import com.orrot.store.cart.adapter.output.jpa.entity.CartItemJpaEntity;
import com.orrot.store.cart.adapter.output.jpa.entity.CartItemJpaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItemJpaEntity, CartItemJpaId> {

}
