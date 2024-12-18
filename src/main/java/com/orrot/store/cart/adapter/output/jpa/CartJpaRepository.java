package com.orrot.store.cart.adapter.output.jpa;

import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.common.jpa.BaseJpaRepository;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartJpaRepository extends BaseJpaRepository<CartJpaEntity, Long>, JpaRepository<CartJpaEntity, Long> {

    @NonNull
    @EntityGraph("Cart.fullInfo")
    Optional<CartJpaEntity> findById(@NonNull Long cartId);
}
