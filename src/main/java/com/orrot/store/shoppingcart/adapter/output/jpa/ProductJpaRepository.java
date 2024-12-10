package com.orrot.store.shoppingcart.adapter.output.jpa;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
}
