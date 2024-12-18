package com.orrot.store.product.adapter.input.json.mapper;

import com.orrot.store.product.adapter.input.json.ProductView;
import com.orrot.store.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductJsonViewMapper {

    ProductView mapToView(Product domain);
}
