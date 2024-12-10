package com.orrot.store.shoppingcart.adapter.output.jpa.mapper;

import com.orrot.store.shoppingcart.adapter.output.jpa.entity.ProductJpaEntity;
import com.orrot.store.shoppingcart.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductDomainMapper {

    Product mapToDomain(ProductJpaEntity entity);

    ProductJpaEntity mapToJpaEntity(Product cart);
}
