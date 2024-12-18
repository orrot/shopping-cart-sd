package com.orrot.store.cart.adapter.output.jpa;

import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartJpaEntity, Long> {

    @NonNull
    @EntityGraph("Cart.fullInfo")
    Optional<CartJpaEntity> findById(@NonNull Long cartId);

    @Modifying
    @Query("UPDATE #{#entityName} c SET c.paymentMethod.code = :paymentMethodCode WHERE c.id = :cartId")
    void updatePaymentMethod(
            @Param("cartId") Long cartId,
            @Param("paymentMethodCode") String paymentMethodCode);
}
