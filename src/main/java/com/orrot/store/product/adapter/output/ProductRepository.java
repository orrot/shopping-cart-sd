package com.orrot.store.product.adapter.output;

import com.orrot.store.product.adapter.output.jpa.ProductJpaRepository;
import com.orrot.store.product.adapter.output.jpa.mapper.ProductDomainMapper;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.port.output.ProductOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Product> findAll(Pageable pageable) {
        // Consider use Specification pattern if more filters/conditions are required
        return productJpaRepository.findAll(pageable)
                .map(productDomainMapper::mapToDomain);
    }
}
