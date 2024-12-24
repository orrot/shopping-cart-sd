package com.orrot.store.cart.adapter.output.jpa;

import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.common.jpa.BaseJpaRepository;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface CartJpaRepository extends BaseJpaRepository<CartJpaEntity, Long> {

    @NonNull
    @EntityGraph("Cart.fullInfo")
    Optional<CartJpaEntity> findById(@NonNull Long cartId);
}
