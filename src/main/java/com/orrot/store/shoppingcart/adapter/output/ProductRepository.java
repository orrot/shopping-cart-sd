package com.orrot.store.shoppingcart.adapter.output;

import com.orrot.store.shoppingcart.adapter.output.jpa.ProductJpaRepository;
import com.orrot.store.shoppingcart.adapter.output.jpa.mapper.ProductDomainMapper;
import com.orrot.store.shoppingcart.domain.model.Product;
import com.orrot.store.shoppingcart.port.output.ProductOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProductRepository implements ProductOutputPort {

    private final ProductJpaRepository productJpaRepository;
    private final ProductDomainMapper productDomainMapper;

    @Override
    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId)
                .map(productDomainMapper::mapToDomain);
    }

    @Override
    public boolean existsById(Long productId) {
        return productJpaRepository.existsById(productId);
    }
}
