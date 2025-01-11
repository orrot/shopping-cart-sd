package com.orrot.store.product.adapter.input;

import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.common.rest.json.IdentityId;
import com.orrot.store.product.adapter.input.json.ProductWrite;
import com.orrot.store.product.adapter.input.json.mapper.ProductJsonViewMapper;
import com.orrot.store.product.port.usecase.CreateProductUseCase;
import com.orrot.store.product.port.usecase.UpdateProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourcesURI.PRODUCTS_URI)
@Tag(name = ResourcesURI.PRODUCTS_TAG)
@RequiredArgsConstructor
public class ProductWriteRestAdapter {

    private final ProductJsonViewMapper mapper;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new client of the store that buy online")
    public IdentityId create(
            @RequestBody ProductWrite productWrite) {

        var product = mapper.mapToDomain(productWrite);
        var createdClient = createProductUseCase
                .createProduct(product);
        return new IdentityId(createdClient.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Updates an existing client of the store")
    public void update(
            @PathVariable("id") Long id,
            @RequestBody ProductWrite productWrite) {

        var product = mapper.mapToDomain(productWrite);
        updateProductUseCase.updateProduct(product.withId(id));
    }
}
