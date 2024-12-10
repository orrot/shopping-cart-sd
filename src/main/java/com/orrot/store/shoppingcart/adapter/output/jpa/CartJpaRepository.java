package com.orrot.store.shoppingcart.adapter.output.jpa;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.CartJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartJpaEntity, Long> {

    @EntityGraph("Cart.withItems")
    @NonNull
    Optional<CartJpaEntity> findById(@NonNull Long cartId);
}
