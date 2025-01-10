package com.orrot.store.product.adapter.output.jpa.mapper;

import com.orrot.store.common.jpa.BaseDomainMapper;
import com.orrot.store.product.adapter.output.jpa.entity.ProductJpaEntity;
import com.orrot.store.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductDomainMapper extends BaseDomainMapper<Product, ProductJpaEntity> {

    Product mapToDomain(ProductJpaEntity entity);

    ProductJpaEntity mapToJpaEntity(Product cart);
}
