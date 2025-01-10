package com.orrot.store.product.adapter.output;

import com.orrot.store.common.jpa.BaseDomainRepository;
import com.orrot.store.product.adapter.output.jpa.ProductJpaRepository;
import com.orrot.store.product.adapter.output.jpa.entity.ProductJpaEntity;
import com.orrot.store.product.adapter.output.jpa.mapper.ProductDomainMapper;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.port.output.ProductOutputPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ProductRepository
        extends BaseDomainRepository<Product, ProductJpaEntity, Long>
        implements ProductOutputPort {

    public ProductRepository(ProductJpaRepository productJpaRepository,
                                  ProductDomainMapper productDomainMapper) {
        super(productJpaRepository, productDomainMapper);
    }
}
