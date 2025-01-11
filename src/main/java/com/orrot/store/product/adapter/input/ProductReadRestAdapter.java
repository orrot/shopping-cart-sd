package com.orrot.store.product.adapter.input;

import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.product.adapter.input.json.ProductView;
import com.orrot.store.product.adapter.input.json.mapper.ProductJsonViewMapper;
import com.orrot.store.product.port.usecase.GetProductByIdUseCase;
import com.orrot.store.product.port.usecase.ListProductsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ResourcesURI.PRODUCTS_URI)
@Tag(name = ResourcesURI.PRODUCTS_TAG)
@RequiredArgsConstructor
public class ProductReadRestAdapter {

    private final ProductJsonViewMapper mapper;
    private final ListProductsUseCase listProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all the products")
    public Page<ProductView> listProducts(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        return listProductsUseCase.listProducts(pageable)
                .map(mapper::mapToView);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Product By ID")
    public ProductView findById(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(getProductByIdUseCase::findById)
                .map(mapper::mapToView)
                .orElseThrow();
    }
}
