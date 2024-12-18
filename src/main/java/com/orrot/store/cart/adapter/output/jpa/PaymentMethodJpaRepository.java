package com.orrot.store.cart.adapter.output.jpa;

import com.orrot.store.cart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethodJpaEntity, String> {

}
